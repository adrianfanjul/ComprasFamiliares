package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments


import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentCreateGroupBinding
import es.adrianfg.comprasfamiliares.databinding.FragmentRegisterBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.CreateGroupViewModel
import es.adrianfg.comprasfamiliares.presentation.features.login.vm.RegisterViewModel

@AndroidEntryPoint
class CreateGroupFragment : BaseFragmentDb<FragmentCreateGroupBinding, CreateGroupViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_create_group
    override val viewModel: CreateGroupViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun eventListeners() {
        dataBinding.createGroupExitBtn.setOnClickListener { exit() }
    }

    override fun observeViewModels() {

        viewModel.errorName.observe(viewLifecycleOwner, ::errorName)
        viewModel.group.observe(viewLifecycleOwner, ::createSucess)
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_create_group, varargs = message)).show()
    }

    fun exit() {
        val directions = CreateGroupFragmentDirections.createGroupFragmentToGroupFragment()
        navigate(directions)
    }

    private fun createSucess(group: Group?) {
        snack(SnackbarMessage(R.string.groups_create_group,varargs = group?.name)).show()
        exit()
    }

    private fun errorName(isValid: Boolean?) {
        isValid?.let {
            when (isValid) {
                true -> dataBinding.createGroupInputLayoutName.error = getString(R.string.register_error_name)
                false -> dataBinding.createGroupInputLayoutName.error = null
            }
        }
    }
}

