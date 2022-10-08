package es.adrianfg.comprasfamiliares.domain.repository

import androidx.appcompat.widget.AppCompatImageView
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    fun getListProducts(group: Group): Flow<List<Product>>
    fun registerProduct(product: Product,imageView: AppCompatImageView): Flow<Product>
    fun deleteProduct(product: Product): Flow<List<Product>>
    fun deleteAllProducts(group: Group): Flow<List<Product>>
}

