package es.adrianfg.comprasfamiliares.presentation.features.groups.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentGroupBinding
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.GroupViewModel

@AndroidEntryPoint
class GroupFragment : BaseFragmentDb<FragmentGroupBinding, GroupViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_group
    override val viewModel: GroupViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }


}

