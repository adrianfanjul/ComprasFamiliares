package es.adrianfg.comprasfamiliares.presentation.features.login.fragments


import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentRegisterBinding
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.login.vm.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : BaseFragmentDb<FragmentRegisterBinding, RegisterViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_register
    override val viewModel: RegisterViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun eventListeners() {
        dataBinding.registerExitBtn.setOnClickListener { exit() }
    }

    override fun observeViewModels() {
        viewModel.errorEmail.observe(viewLifecycleOwner, ::errorUserName)
        viewModel.errorPass.observe(viewLifecycleOwner, ::errorPass)
        viewModel.errorName.observe(viewLifecycleOwner, ::errorName)
        viewModel.user.observe(viewLifecycleOwner, ::registerSucess)
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_register, varargs = message)).show()
    }

    fun exit() {
        val directions = RegisterFragmentDirections.registerFragmentToLoginFragment()
        navigate(directions)
    }

    private fun registerSucess(user: User?) {
        snack(SnackbarMessage(R.string.register_registred_user,varargs = user?.email.toString())).show()
        val directions = RegisterFragmentDirections.registerFragmentToLoginFragment()
        navigate(directions)
    }

    private fun errorUserName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.registerInputLayoutEmail.error = getString(R.string.register_error_user)
                false -> dataBinding.registerInputLayoutEmail.error = null
            }
        }
    }
    private fun errorPass(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.registerInputLayoutPass.error = getString(R.string.register_error_pass)
                false -> dataBinding.registerInputLayoutPass.error = null
            }
        }
    }
    private fun errorName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.registerInputLayoutName.error = getString(R.string.register_error_name)
                false -> dataBinding.registerInputLayoutName.error = null
            }
        }
    }
}

