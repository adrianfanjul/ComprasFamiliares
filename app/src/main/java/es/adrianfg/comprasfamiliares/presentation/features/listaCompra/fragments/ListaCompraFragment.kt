package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraBinding
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.presentation.features.groups.fragments.GroupFragmentDirections
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraViewModel

@AndroidEntryPoint
class ListaCompraFragment : BaseFragmentDb<FragmentListaCompraBinding, ListaCompraViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_lista_compra
    override val viewModel: ListaCompraViewModel by viewModels()

    override fun setBindingLayout() {
        dataBinding.viewModel = viewModel
    }
    override fun eventListeners() {
        dataBinding.listaCompraAddBtn.setOnClickListener { addProduct() }
    }

    fun addProduct() {
        val directions = ListaCompraFragmentDirections.listaCompraFragmentToListaCompraAddFragment()
        navigate(directions)
    }

}

