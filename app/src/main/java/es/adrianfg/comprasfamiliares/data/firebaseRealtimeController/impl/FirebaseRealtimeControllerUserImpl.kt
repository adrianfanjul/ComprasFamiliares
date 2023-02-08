package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerUser
import es.adrianfg.comprasfamiliares.data.response.UserResponseItem
import es.adrianfg.comprasfamiliares.data.response.UsersResponse
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


class FirebaseRealtimeControllerUserImpl @Inject constructor() : FirebaseRealtimeControllerUser {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val maxTimeout= 3000L

    override suspend fun register(user: User, context: Context): UserResponseItem {
        val userResponse = UserResponseItem(user.email, user.pass, user.name, user.surName, user.age)
        try {

            val result = withTimeout(maxTimeout) {database.orderByChild("email").equalTo(user.email).get().await()}
            if (result.exists()) {
                throw Error(context.resources?.getString(R.string.error_register_repeat_user))

            } else {
                database.push().setValue(userResponse)
            }

            return userResponse

        } catch (ex: Exception) {
            when(ex) {
                is TimeoutCancellationException -> {
                    throw Error(context.resources?.getString(R.string.error_time_out))
                }
                else -> throw Error(ex.message)
            }
        }
    }

    override suspend fun logIn(user: String, pass: String, context: Context): UserResponseItem {
        var userResponse = UserResponseItem()
        try {
            val result = withTimeout(maxTimeout) {database.orderByChild("email").equalTo(user).get().await() }
            if (result.exists()) {
                for (i in result.children) {
                    userResponse = i.getValue(UserResponseItem::class.java) ?: UserResponseItem()
                    if (!userResponse.pass.equals(pass)) {
                        throw Error(context.resources?.getString(R.string.error_login_incorrect_paswword))
                    }
                }
            } else {
                throw Error(context.resources?.getString(R.string.error_login_incorrect_user))
            }
            return userResponse

        } catch (ex: Exception) {
            when(ex) {
                is TimeoutCancellationException -> {
                    throw Error(context.resources?.getString(R.string.error_time_out))
                }
                else -> throw Error(ex.message)
            }
        }
    }

    override suspend fun getListUsers(context: Context): UsersResponse {
        val listUsers = mutableListOf<UserResponseItem>()
        try {
            val result = withTimeout(maxTimeout) {database.orderByChild("email").get().await()}
            if (result.exists()) {
                for (user in result.children) {
                    listUsers.add(
                        user.getValue(UserResponseItem::class.java) ?: UserResponseItem()
                    )
                }

            } else {
                throw Error(context.resources?.getString(R.string.error_users_empty))
            }

            return UsersResponse(listUsers.toList())

        } catch (ex: Exception) {
            when(ex) {
                is TimeoutCancellationException -> {
                    throw Error(context.resources?.getString(R.string.error_time_out))
                }
                else -> throw Error(ex.message)
            }
        }
    }
}