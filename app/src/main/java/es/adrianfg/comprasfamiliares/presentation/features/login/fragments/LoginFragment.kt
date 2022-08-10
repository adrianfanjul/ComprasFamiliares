package es.adrianfg.comprasfamiliares.presentation.features.login.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentLoginBinding
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.login.vm.LoginViewModel

@AndroidEntryPoint
class LoginFragment : BaseFragmentDb<FragmentLoginBinding, LoginViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun observeViewModels() {
        viewModel.errorUser.observe(viewLifecycleOwner, ::errorUserName)
        viewModel.user.observe(viewLifecycleOwner, ::loginSucess)
    }


    private fun loginSucess(user: User?) {
        //val directions = LoginFragmentDirections.loginFragmentToMainActivity(user)
        //navigate(directions)
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

