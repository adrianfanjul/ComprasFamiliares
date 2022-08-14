package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraBinding
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraViewModel

@AndroidEntryPoint
class ListaCompraFragment : BaseFragmentDb<FragmentListaCompraBinding, ListaCompraViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_group
    override val viewModel: ListaCompraViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }


}

