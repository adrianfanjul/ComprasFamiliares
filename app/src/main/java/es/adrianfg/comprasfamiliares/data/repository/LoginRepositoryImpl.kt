package es.adrianfg.comprasfamiliares.data.repository
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeController
import es.adrianfg.comprasfamiliares.data.mappers.mapToUser
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseRealtimeController: FirebaseRealtimeController,
    @ApplicationContext private val context:Context,
) : LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> = flow {
        emit(firebaseRealtimeController.logIn(userName,password).mapToUser())}

}