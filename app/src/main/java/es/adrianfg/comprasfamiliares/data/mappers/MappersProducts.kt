package es.adrianfg.comprasfamiliares.data.mappers

import es.adrianfg.comprasfamiliares.data.response.ProductResponseItem
import es.adrianfg.comprasfamiliares.data.response.ProductsResponse
import es.adrianfg.comprasfamiliares.domain.models.Product

fun ProductsResponse.mapToProducts() =
    results?.map {
        Product(
            image = it?.image ?: "",
            name = it?.name ?: "",
            description = it?.description ?: "",
            amount = it?.amount ?:-1,
            user= it?.user ?:""
        )
    }.orEmpty()

fun ProductResponseItem.mapToProduct() =
        Product(
            image = this.image ?: "",
            name = this.name ?: "",
            description = this.description ?: "",
            amount = this.amount ?:-1,
            user= this.user ?:""
        )



