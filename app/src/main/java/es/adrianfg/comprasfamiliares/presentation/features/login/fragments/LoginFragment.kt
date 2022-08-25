package es.adrianfg.comprasfamiliares.presentation.features.login.fragments

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentLoginBinding
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.login.vm.LoginViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragmentDb<FragmentLoginBinding, LoginViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel

    }

    override fun eventListeners() {
        dataBinding.registerUserBtn.setOnClickListener { register() }
    }

    override fun observeViewModels() {
        viewModel.errorUser.observe(viewLifecycleOwner, ::errorUserName)
        viewModel.user.observe(viewLifecycleOwner, ::loginSucess)
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_login, varargs = message)).show()
    }

    fun register() {
        val directions = LoginFragmentDirections.loginFragmentToRegisterFragment()
        navigate(directions)
    }

    private fun loginSucess(user: User?) {
        val directions = LoginFragmentDirections.loginFragmentToGroupActivity(user)
        navigate(directions)
    }

    private fun errorUserName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.loginInputLayoutUserName.error = getString(R.string.login_error_user)
                false -> dataBinding.loginInputLayoutUserName.error = null
            }
        }
    }


}

