package es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO

import es.adrianfg.comprasfamiliares.data.response.UserResponse

interface FirebaseRealtimeDAO {
    fun registerUser()
    suspend fun logIn(user: String, pass: String): UserResponse
}