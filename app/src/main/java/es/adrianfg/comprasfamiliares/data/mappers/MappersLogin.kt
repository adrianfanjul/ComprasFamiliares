package es.adrianfg.comprasfamiliares.data.mappers

import com.google.firebase.auth.AuthResult
import es.adrianfg.comprasfamiliares.data.response.UserResponse
import es.adrianfg.comprasfamiliares.domain.models.User

fun UserResponse.mapToUser() = User(
    name = this.name ?: "",
    surName = this.surName ?: "sin apellido",
    age = 23
)

fun AuthResult.mapToUser() = User(
    name = (additionalUserInfo?.profile?.get("first_name") ?: "") as String,
    surName = (additionalUserInfo?.profile?.get("last_name") ?: "") as String,
    age = -1
)