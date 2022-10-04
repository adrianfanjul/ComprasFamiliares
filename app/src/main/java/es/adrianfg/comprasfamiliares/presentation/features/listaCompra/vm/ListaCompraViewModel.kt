package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.deleteStorageImage
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.usecase.DeleteProductUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.GetListProductsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaCompraViewModel @Inject constructor(
    private val getListProductsUseCase: GetListProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : BaseViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList
    private val _boughtProduct = MutableLiveData<Product>()
    val boughtProduct: LiveData<Product> get() = _boughtProduct

    fun loadProductsList(group: Group) {
        viewModelScope.launch {
            getListProductsUseCase.execute(
                GetListProductsUseCase.Params(group)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _productList.value = it}
        }
    }

    fun buyProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase.execute(
                DeleteProductUseCase.Params(product)
            )
                .onStart { _loading.value = true
                            deleteStorageImage(product.image)}
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect {_boughtProduct.value=it}
        }
    }

}