package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController

import es.adrianfg.comprasfamiliares.data.response.UserResponse

interface FirebaseRealtimeController {
    fun registerUser()
    suspend fun logIn(user: String, pass: String): UserResponse
}