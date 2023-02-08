package es.adrianfg.comprasfamiliares.core.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import es.adrianfg.comprasfamiliares.R

abstract class BaseFragmentDb<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM
    protected lateinit var dataBinding: DB

    @LayoutRes
    abstract fun getLayout(): Int

    open fun eventListeners() {
        //implement in class
    }

    open fun initViewModels() {
        //implement in class
    }

    open fun observeViewModels() {
        //implement in class
    }

    open fun setBindingLayout() {
        //implement in class
    }

    open fun showError(message: String?) {
        //implemented in class
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding = DataBindingUtil.inflate<DB>(inflater, getLayout(), container, false).apply {
            lifecycleOwner = this@BaseFragmentDb
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventListeners()
        initViewModels()
        observeViewModels()
        setBindingLayout()
        handleError()
    }

    private fun handleError() {
        viewModel.error.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let {
                if (it.message.equals(resources.getString(R.string.error_time_out))) {
                    val builder = AlertDialog.Builder(view?.context)
                    errorTimeoutDialog(builder)
                }else{
                    showError(it.message)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.unbind()
    }

    private fun errorTimeoutDialog(builder: AlertDialog.Builder) {
        builder.setTitle(resources.getString(R.string.error_time_out_title))
        builder.setMessage(resources.getString(R.string.error_time_out))
        builder.setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
            navigate(R.id.to_loginActivity)
        }
        builder.show()
    }

    /**
     * Prevent IllegalArgumentException cannot be found from the current destination Destination
     */
    protected fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

    fun navigate(destination: Int, bundle: Bundle? = null) = with(findNavController()) {
        currentDestination?.getAction(destination)?.let { navigate(destination, bundle) }
    }

    fun navigateBack() = with(findNavController()) {
        currentDestination?.let { navigateUp() }
    }


}