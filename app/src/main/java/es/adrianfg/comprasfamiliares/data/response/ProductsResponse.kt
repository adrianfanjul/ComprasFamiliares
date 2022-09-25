package es.adrianfg.comprasfamiliares.data.response

import com.google.gson.annotations.SerializedName


data class ProductsResponse(
    @SerializedName("results")
    val results: List<ProductResponseItem?> ? = null
)