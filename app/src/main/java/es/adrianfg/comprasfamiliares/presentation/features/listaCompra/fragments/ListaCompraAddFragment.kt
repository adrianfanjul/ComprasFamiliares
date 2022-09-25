package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentCreateGroupBinding
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraAddBinding
import es.adrianfg.comprasfamiliares.presentation.features.groups.vm.CreateGroupViewModel
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraAddViewModel


@AndroidEntryPoint
class ListaCompraAddFragment : BaseFragmentDb<FragmentListaCompraAddBinding, ListaCompraAddViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_lista_compra_add
    override val viewModel: ListaCompraAddViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }

    override fun eventListeners() {

    }

    override fun observeViewModels() {

    }

}


