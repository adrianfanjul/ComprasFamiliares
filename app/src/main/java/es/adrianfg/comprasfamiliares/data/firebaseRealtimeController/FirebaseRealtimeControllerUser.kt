package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController

import android.content.Context
import es.adrianfg.comprasfamiliares.data.response.UserResponseItem
import es.adrianfg.comprasfamiliares.data.response.UsersResponse
import es.adrianfg.comprasfamiliares.domain.models.User

interface FirebaseRealtimeControllerUser {
    suspend fun register(user: User,context: Context): UserResponseItem
    suspend fun logIn(user: String, pass: String,context: Context): UserResponseItem
    suspend fun getListUsers(context: Context): UsersResponse
}