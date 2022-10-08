package es.adrianfg.comprasfamiliares.data.repository

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
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

    override fun registerProduct(product: Product,imageView: AppCompatImageView): Flow<Product> = flow {
        emit(firebaseRealtimeControllerProduct.register(product,imageView,context).mapToProduct())
    }

    override fun deleteProduct(product: Product): Flow<List<Product>> = flow {
        emit(firebaseRealtimeControllerProduct.deleteProduct(product, context).mapToProducts())
    }

    override fun deleteAllProducts(group: Group): Flow<List<Product>> = flow {
        emit(firebaseRealtimeControllerProduct.deleteAllProducts(group, context).mapToProducts())
    }

    override fun getListProducts(group: Group): Flow<List<Product>> = flow {
        emit(firebaseRealtimeControllerProduct.getListProducts(group,context).mapToProducts())
    }
}