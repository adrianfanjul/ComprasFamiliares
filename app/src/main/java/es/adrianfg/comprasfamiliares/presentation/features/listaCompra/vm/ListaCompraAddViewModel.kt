package es.adrianfg.comprasfamiliares.presentation.features.listaCompra.vm

import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.adrianfg.comprasfamiliares.core.base.BaseViewModel
import es.adrianfg.comprasfamiliares.core.base.SingleEvent
import es.adrianfg.comprasfamiliares.core.extension.combine
import es.adrianfg.comprasfamiliares.core.extension.getTmpFileUri
import es.adrianfg.comprasfamiliares.core.extension.isValidName
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.usecase.SetProductsUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ListaCompraAddViewModel @Inject constructor(
    private val setProductsUseCase: SetProductsUseCase,
) : BaseViewModel() {

    lateinit var imageView: AppCompatImageView
    private val minlengh = 1
    val description = MutableLiveData("")
    val name = MutableLiveData("")
    val image = MutableLiveData("")
    val amount = MutableLiveData("")
    val user = MutableLiveData("")
    val group = MutableLiveData("")

    val errorName = liveData<Boolean> {
        emitSource(
            Transformations.map(enableBtn) {
                return@map it && name.value?.isValidName() == false
            }
        )
    }

    val enableBtn: LiveData<Boolean> = name.combine(amount) { name, amount ->
        return@combine name.length > minlengh && amount.isNotEmpty()
    }

    private val _product = MutableLiveData<Product>()
    val product get() = _product

    fun create() {
        image.value = getImageRoute()
        viewModelScope.launch {
            setProductsUseCase.execute(
                SetProductsUseCase.Params(
                    Product(
                        name.value ?: "",
                        description.value ?: "",
                        amount.value?.toInt() ?: -1,
                        image.value ?: "",
                        user.value ?: "",
                        group.value ?:""
                    ),imageView
                )
            )
                .onStart { _loading.value = true }
                .onCompletion { _loading.value = false }
                .catch { _error.value = SingleEvent(it) }
                .collect {_product.value = it}
        }
    }

    private fun getImageRoute():String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS")
        val formatted = current.format(formatter)
        return "Products/${group.value}/${name.value}${formatted}.jpg"
    }

    fun getTmpFile(context: Context): Uri {
        return getTmpFileUri(context)
    }

}

