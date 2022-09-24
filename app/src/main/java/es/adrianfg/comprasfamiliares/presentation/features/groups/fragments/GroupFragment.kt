package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.base.recycler.BaseRvAdapter
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
    val sharedViewModel: GroupMainViewModel by activityViewModels()
    private val adapter by lazy {
        BaseRvAdapter<Group>(R.layout.item_group_list) { group ->
            group?.let {
                val directions = GroupFragmentDirections.groupFragmentToListaCompraActivity(it,sharedViewModel.user.value)
                navigate(directions)
            }
        }
    }

    override fun eventListeners() {
        dataBinding.groupsRv.adapter = adapter
        dataBinding.groupsFabAdd.setOnClickListener { createGroup() }
    }

    override fun initViewModels() {
        viewModel.loadGroupsList(sharedViewModel.user.value?:User("","","","",-1))
    }

    override fun observeViewModels() {
        viewModel.groupList.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }

    fun createGroup() {
        val directions = GroupFragmentDirections.groupFragmentToCreateGroupFragment()
        navigate(directions)
    }

}

