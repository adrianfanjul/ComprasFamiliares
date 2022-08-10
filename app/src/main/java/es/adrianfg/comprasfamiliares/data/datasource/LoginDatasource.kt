package es.adrianfg.comprasfamiliares.data.datasource

import es.adrianfg.comprasfamiliares.data.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface LoginDatasource {
    fun registerUser()
    fun logOut()
    fun logIn(user: String, pass: String): Flow<UserResponse>
}