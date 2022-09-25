package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.GroupsRepository
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import es.adrianfg.comprasfamiliares.domain.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListProductsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val productsRepository: ProductsRepository
) : FlowUseCase<GetListProductsUseCase.Params, List<Product>>(dispatcher) {

    override fun execute(params: Params): Flow<List<Product>> = productsRepository.getListProducts(params.group)

    data class Params(val group: Group)

}