package es.adrianfg.comprasfamiliares.data.mappers

import es.adrianfg.comprasfamiliares.data.response.GroupResponseItem
import es.adrianfg.comprasfamiliares.data.response.GroupsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group

fun GroupsResponse.mapToGroups() =
    results?.map {
        Group(
            image = it?.image ?: "",
            name = it?.name ?: "",
            description = it?.description ?: "",
            users = it?.users.orEmpty(),
            createUser = it?.createUser ?: ""
        )
    }.orEmpty()

fun GroupResponseItem.mapToGroup() =
        Group(
            image = this.image ?: "",
            name = this.name ?: "",
            description = this.description ?: "",
            users = this.users.orEmpty(),
            createUser = this.createUser ?: "",
        )



