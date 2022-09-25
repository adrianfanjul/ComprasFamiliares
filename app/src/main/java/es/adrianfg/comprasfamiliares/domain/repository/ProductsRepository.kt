package es.adrianfg.comprasfamiliares.domain.repository

import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    fun getListProducts(group: Group): Flow<List<Product>>
    fun registerProduct(product: Product): Flow<Product>
}

