package es.adrianfg.comprasfamiliares.presentation.features.login.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentRegisterBinding
import es.adrianfg.comprasfamiliares.presentation.features.login.vm.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : BaseFragmentDb<FragmentRegisterBinding, RegisterViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_register
    override val viewModel: RegisterViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }


}

