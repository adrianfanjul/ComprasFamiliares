package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.base.recycler.BaseRvAdapter
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraMainViewModel
import es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm.ListaCompraViewModel

@AndroidEntryPoint
class ListaCompraFragment : BaseFragmentDb<FragmentListaCompraBinding, ListaCompraViewModel>() {

    override fun getLayout(): Int = R.layout.fragment_lista_compra
    override val viewModel: ListaCompraViewModel by viewModels()
    private val sharedViewModel: ListaCompraMainViewModel by activityViewModels()
    private val adapter by lazy {
        BaseRvAdapter<Product>(R.layout.item_product_list) { product ->
            product?.let {
                Log.e("products",it.name)
            }
        }
    }


    override fun eventListeners() {
        dataBinding.productsRv.adapter = adapter
        dataBinding.listaCompraAddBtn.setOnClickListener { addProduct() }
    }

    override fun initViewModels() {
        viewModel.loadProductsList(sharedViewModel.group.value?: Group("","","", emptyList()))
    }

    override fun observeViewModels() {
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.items = it
        }
    }
    private fun addProduct() {
        val directions = ListaCompraFragmentDirections.listaCompraFragmentToListaCompraAddFragment()
        navigate(directions)
    }

}

