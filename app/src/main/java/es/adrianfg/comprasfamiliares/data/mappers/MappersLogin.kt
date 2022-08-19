package es.adrianfg.comprasfamiliares.data.mappers

import es.adrianfg.comprasfamiliares.data.response.UserResponse
import es.adrianfg.comprasfamiliares.domain.models.User


fun UserResponse.mapToUser() = User(
    name = this.name ?: "",
    surName = this.surName ?: "sin apellido",
    age = this.age ?: -1
)
