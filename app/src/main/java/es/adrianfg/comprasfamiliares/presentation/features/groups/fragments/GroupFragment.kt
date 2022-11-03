package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentGroupBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.adapters.GroupsRvAdapter
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.GroupMainViewModel
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.GroupViewModel

@AndroidEntryPoint
class GroupFragment : BaseFragmentDb<FragmentGroupBinding, GroupViewModel> () {
    override val viewModel: GroupViewModel by viewModels()
    override fun getLayout(): Int = R.layout.fragment_group
    private val sharedViewModel: GroupMainViewModel by activityViewModels()
    private val adapter by lazy {
        GroupsRvAdapter<Group>(sharedViewModel.user.value?.email?: "",requireContext(),R.layout.item_group_list) { group, button ->
            group?.let {
                if(button==1){
                    viewModel.buyAllProduct(it)
                    viewModel.deleteGroup(it)
                }

                if (button==2) {
                    val directions = GroupFragmentDirections.groupFragmentToListaCompraActivity(it,sharedViewModel.user.value)
                    navigate(directions)
                }
            }
        }
    }

    override fun eventListeners() {
        dataBinding.groupsRv.adapter = adapter
        dataBinding.groupsFabAdd.setOnClickListener { createGroup() }
    }

    override fun initViewModels() {
        viewModel.loadGroupsList(sharedViewModel.user.value ?: User("", "", "", "", -1))
    }

    override fun observeViewModels() {
        viewModel.groupList.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }

    override fun showError(message: String?) {
        snack(SnackbarMessage(R.string.error_groups, varargs = message)).show()
        if (message.equals(context?.resources?.getString(R.string.error_time_out))){
            navigate(R.id.to_loginActivity)
        }
    }

    private fun createGroup() {
        val directions = GroupFragmentDirections.groupFragmentToCreateGroupFragment()
        navigate(directions)
    }
}

