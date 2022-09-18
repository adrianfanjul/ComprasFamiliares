package es.adrianfg.comprasfamiliares.data.mappers

import es.adrianfg.comprasfamiliares.data.response.UserResponseItem
import es.adrianfg.comprasfamiliares.data.response.UsersResponse
import es.adrianfg.comprasfamiliares.domain.models.User


fun UserResponseItem.mapToUser() = User(
    email= this.email ?: "",
    pass = this.pass ?: "",
    name = this.name ?: "",
    surName = this.surName ?: "",
    age = this.age ?: -1
)
fun UsersResponse.mapToUsers() =
    results?.map {
        User(
            email= it?.email ?: "",
            pass = it?.pass ?: "",
            name = it?.name ?: "",
            surName = it?.surName ?: "",
            age = it?.age?: -1
        )
    }.orEmpty()