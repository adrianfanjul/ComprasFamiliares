package es.adrianfg.comprasfamiliares.domain.repository

import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun logIn(userName:String, password:String): Flow<User>
}