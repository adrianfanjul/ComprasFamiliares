package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetProductsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val productsRepository: ProductsRepository
) : FlowUseCase<SetProductsUseCase.Params, Product>(dispatcher) {

    override fun execute(params: Params): Flow<Product> = productsRepository.registerProduct(params.product)

    data class Params(val product: Product)

}