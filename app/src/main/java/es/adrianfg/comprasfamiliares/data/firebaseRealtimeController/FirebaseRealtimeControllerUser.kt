package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController

import android.content.Context
import es.adrianfg.comprasfamiliares.data.response.UserResponse
import es.adrianfg.comprasfamiliares.domain.models.User

interface FirebaseRealtimeControllerUser {
    suspend fun register(user: User,context: Context): UserResponse
    suspend fun logIn(user: String, pass: String,context: Context): UserResponse
}