package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import es.adrianfg.comprasfamiliares.data.response.GroupResponseItem
import es.adrianfg.comprasfamiliares.data.response.GroupsResponse
import es.adrianfg.comprasfamiliares.data.response.ProductResponseItem
import es.adrianfg.comprasfamiliares.data.response.ProductsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import es.adrianfg.comprasfamiliares.domain.models.User

interface FirebaseRealtimeControllerProduct {
    suspend fun register(product: Product,imageView: AppCompatImageView,context: Context): ProductResponseItem
    suspend fun getListProducts(group: Group,context: Context): ProductsResponse
    suspend fun deleteProduct(product: Product,context: Context): ProductsResponse
    suspend fun deleteAllProducts(group: Group,context: Context): ProductsResponse
}