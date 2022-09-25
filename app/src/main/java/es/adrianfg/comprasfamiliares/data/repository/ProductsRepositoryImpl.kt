package es.adrianfg.comprasfamiliares.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerProduct
import es.adrianfg.comprasfamiliares.data.mappers.mapToProduct
import es.adrianfg.comprasfamiliares.data.mappers.mapToProducts
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val firebaseRealtimeControllerProduct: FirebaseRealtimeControllerProduct,
    @ApplicationContext private val context: Context,
) : ProductsRepository {

    override fun registerProduct(product: Product): Flow<Product> = flow {
        emit(firebaseRealtimeControllerProduct.register(product, context).mapToProduct())
    }

    override fun getListProducts(group: Group): Flow<List<Product>> = flow {
        emit(firebaseRealtimeControllerProduct.getListProducts(group,context).mapToProducts())
    }
}