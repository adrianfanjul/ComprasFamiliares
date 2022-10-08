package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.fragments

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.BaseFragmentDb
import es.adrianfg.comprasfamiliares.core.base.recycler.BaseRvAdapter
import es.adrianfg.comprasfamiliares.core.extension.snack
import es.adrianfg.comprasfamiliares.databinding.FragmentListaCompraBinding
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.SnackbarMessage
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
                viewModel.buyProduct(it)
            }
        }
    }

    override fun eventListeners() {
        dataBinding.productsRv.adapter = adapter
        dataBinding.listaCompraAddBtn.setOnClickListener { addProduct() }
        dataBinding.listaCompraAllBtn.setOnClickListener { buyAllProducts()}
    }

    override fun initViewModels() {
        viewModel.loadProductsList(sharedViewModel.group.value?: Group("","","", emptyList()))
    }

    override fun observeViewModels() {
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.items = it
        }
        viewModel.boughtProduct.observe(viewLifecycleOwner,::buySuccess)
    }

    private fun buySuccess(product: Product?) {
        snack(SnackbarMessage(R.string.products_bought, varargs = product?.name)).show()
    }

    private fun addProduct() {
        val directions = ListaCompraFragmentDirections.listaCompraFragmentToListaCompraAddFragment()
        navigate(directions)
    }

    private fun buyAllProducts() {
        viewModel.buyAllProduct(sharedViewModel.group.value?: Group("","","", emptyList()))
    }

}

