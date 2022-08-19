package es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.impl

import android.util.Log
import com.google.firebase.database.*
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.FirebaseRealtimeDAO
import es.adrianfg.comprasfamiliares.data.response.UserResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseRealtimeDAOImpl @Inject constructor() : FirebaseRealtimeDAO {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    override fun registerUser() {
        //TODO("Not yet implemented")
    }

    override suspend fun logIn(user: String, pass: String): UserResponse {

        var userResponse=UserResponse()
        return try {
            val result = database.orderByChild("email").equalTo(user).get().await()
            if(result.exists()){
                for (i in result.children){
                    userResponse = i.getValue(UserResponse::class.java)?: UserResponse()
                    if (!userResponse.pass.equals(pass)){
                        Log.e("firebase", "El usuario o contraseña son incorrectos")
                        userResponse=UserResponse()
                    }
                }
                userResponse
            } else{
                Log.e("firebase", "El usuario no existe")
                userResponse
            }

        } catch (e: Exception) {
            Log.e("firebase", "Error getting data", e)
            userResponse
        }
    }
}