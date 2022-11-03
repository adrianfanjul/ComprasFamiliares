package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerProduct
import es.adrianfg.comprasfamiliares.data.response.ProductResponseItem
import es.adrianfg.comprasfamiliares.data.response.ProductsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.Product
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class FirebaseRealtimeControllerProductImpl @Inject constructor(
    private val storageImages: StorageImages
) :  FirebaseRealtimeControllerProduct {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")
    private val maxTimeout= 3000L

    override suspend fun register(product: Product,imageView: AppCompatImageView, context: Context): ProductResponseItem {
        val productResponseItem = ProductResponseItem(product.name, product.description,product.amount, product.image, product.user,product.group)

        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").equalTo(product.name).get().await()

                if (result.exists()) {
                    for (res in result.children) {
                        val productResponse =
                            res.getValue(ProductResponseItem::class.java) ?: ProductResponseItem()
                        if (productResponse.group.equals(product.group)) {
                            throw Error(context.resources?.getString(R.string.error_products_repeat_name))
                        } else {
                            storageImages.uploadStorageImage(product.image, imageView)
                            database.push().setValue(productResponseItem)
                        }
                    }
                } else {
                    storageImages.uploadStorageImage(product.image, imageView)
                    database.push().setValue(productResponseItem)
                }
            }
            return productResponseItem

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

    override suspend fun getListProducts(group: Group, context: Context): ProductsResponse {
        try {
            return getList(group.name,context)

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }

    override suspend fun deleteProduct(product: Product, context: Context): ProductsResponse {
        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").equalTo(product.name).get().await()

                if (result.exists()) {
                    for (res in result.children) {
                        val productResponse =
                            res.getValue(ProductResponseItem::class.java) ?: ProductResponseItem()
                        if (productResponse.group.equals(product.group)) {
                            storageImages.deleteStorageImage(product.image)
                            res.key?.let { database.child(it).removeValue() }
                        } else {
                            throw Error(context.resources?.getString(R.string.error_products_not_found))
                        }
                    }
                } else {
                    throw Error(context.resources?.getString(R.string.error_products_not_found))
                }
            }
            return getList(product.group,context)

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

    override suspend fun deleteAllProducts(group: Group, context: Context): ProductsResponse {
        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").get().await()

                if (result.exists()) {
                    storageImages.deleteAllStorageImages("Products/${group.name}/")
                    for (res in result.children) {
                        val productResponse =
                            res.getValue(ProductResponseItem::class.java) ?: ProductResponseItem()
                        if (productResponse.group.equals(group.name)) {
                            res.key?.let { database.child(it).removeValue() }
                        }
                    }
                }
            }
            return getList(group.name,context)

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

    private suspend fun getList(name: String,context: Context): ProductsResponse {
        val listProducts = mutableListOf<ProductResponseItem>()
        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").get().await()
                if (result.exists()) {
                    for (product in result.children) {
                        val productResponse = product.getValue(ProductResponseItem::class.java)
                            ?: ProductResponseItem()
                        if (productResponse.group.equals(name)) {
                            listProducts.add(
                                product.getValue(ProductResponseItem::class.java)
                                    ?: ProductResponseItem()
                            )
                        }
                    }
                }
            }
            return ProductsResponse(listProducts.toList())

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

}