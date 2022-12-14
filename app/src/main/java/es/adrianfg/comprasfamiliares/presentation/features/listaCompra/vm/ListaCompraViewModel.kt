package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.sendEmail
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.usecase.DeleteAllProductsUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.DeleteProductUseCase
import es.adrianfg.comprasfamiliares.domain.usecase.GetListProductsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class ListaCompraViewModel @Inject constructor(
    private val getListProductsUseCase: GetListProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase
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
                .collect { _productList.value = it }
        }
    }

    fun buyProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase.execute(
                DeleteProductUseCase.Params(product)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect {
                    _productList.value = it
                    _boughtProduct.value = product
                }
        }
    }

    fun buyAllProduct(group: Group) {
        viewModelScope.launch {
            deleteAllProductsUseCase.execute(
                DeleteAllProductsUseCase.Params(group)
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect { _productList.value = it }
        }
    }

    fun reloadList(group: Group) {
        val myHandler = android.os.Handler(Looper.getMainLooper())
        myHandler.post(object : Runnable {
            override fun run() {
                loadProductsList(group)
                myHandler.postDelayed(this, 5000 /*5 segundos*/)
            }
        })
    }

}