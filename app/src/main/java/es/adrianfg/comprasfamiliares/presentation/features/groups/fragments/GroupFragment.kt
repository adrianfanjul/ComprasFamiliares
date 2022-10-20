package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.base.recycler.GroupsRvAdapter
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentGroupBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.GroupMainViewModel
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.GroupViewModel


@AndroidEntryPoint
class GroupFragment : BaseFragmentDb<FragmentGroupBinding, GroupViewModel>() {

    override val viewModel: GroupViewModel by viewModels()
    override fun getLayout(): Int = R.layout.fragment_group
    private val sharedViewModel: GroupMainViewModel by activityViewModels()
    private val adapter by lazy {
        GroupsRvAdapter<Group>(sharedViewModel.user.value?.email?: "",R.layout.item_group_list) { group, button ->
            group?.let {
                if(button==1){
                    viewModel.buyAllProduct(it)
                    viewModel.deleteGroup(it)
                    snack(SnackbarMessage(R.string.groups_delete_group, varargs = group.name)).show()
                }

                if (button==2) {
                    val directions = GroupFragmentDirections.groupFragmentToListaCompraActivity(it,sharedViewModel.user.value)
                    navigate(directions)
                }
            }
        }
    }

    override fun eventListeners() {
        adapter.setLogedUser(sharedViewModel.user.value?.email?: "")
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

    private fun createGroup() {
        val directions = GroupFragmentDirections.groupFragmentToCreateGroupFragment()
        navigate(directions)
    }

}

