package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerProduct
import es.adrianfg.comprasfamiliares.data.response.ProductResponseItem
import es.adrianfg.comprasfamiliares.data.response.ProductsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRealtimeControllerProductImpl @Inject constructor() :  FirebaseRealtimeControllerProduct {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")

    override suspend fun register(product: Product, context: Context): ProductResponseItem {
        val productResponseItem = ProductResponseItem(product.name, product.description,product.amount, product.image, product.user,product.group)

        try {
            val result = database.orderByChild("name").equalTo(product.name).get().await()

            if (result.exists()) {
                for (res in result.children) {
                    val productResponse = res.getValue(ProductResponseItem::class.java) ?: ProductResponseItem()
                    if (productResponse.group.equals(product.group)) {
                        throw Error(context.resources?.getString(R.string.error_products_repeat_name))
                    }else{
                        database.push().setValue(productResponseItem)
                    }
                }
            } else {
                database.push().setValue(productResponseItem)
            }

            return productResponseItem

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }

    override suspend fun getListProducts(group: Group, context: Context): ProductsResponse {
        val listProducts = mutableListOf<ProductResponseItem>()
        try {
            val result = database.orderByChild("name").get().await()
            if (result.exists()) {
                for (product in result.children) {
                    val productResponse = product.getValue(ProductResponseItem::class.java) ?: ProductResponseItem()
                    if (productResponse.group.equals(group.name)) {
                        listProducts.add(product.getValue(ProductResponseItem::class.java) ?: ProductResponseItem())
                    }
                }

            } else {
                throw Error(context.resources?.getString(R.string.error_products_empty))
            }
            return ProductsResponse(listProducts.toList())

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }

}