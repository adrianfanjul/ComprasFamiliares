package es.adrianfg.comprasfamiliares.data.mappers

import es.adrianfg.comprasfamiliares.data.response.UserResponse
import es.adrianfg.comprasfamiliares.domain.models.User


fun UserResponse.mapToUser() = User(
    email= this.email ?: "",
    pass = this.pass ?: "",
    name = this.name ?: "",
    surName = this.surName ?: "",
    age = this.age ?: -1
)
