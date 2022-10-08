package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllProductsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val productsRepository: ProductsRepository,

) : FlowUseCase<DeleteAllProductsUseCase.Params, List<Product>>(dispatcher) {
    override fun execute(params: Params):Flow<List<Product>> {

        return productsRepository.deleteAllProducts(params.group)
    }

    data class Params(val group: Group)

}