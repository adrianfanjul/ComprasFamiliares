package es.adrianfg.comprasfamiliares.data.repository
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerUser
import es.adrianfg.comprasfamiliares.data.mappers.mapToGroups
import es.adrianfg.comprasfamiliares.data.mappers.mapToUser
import es.adrianfg.comprasfamiliares.data.mappers.mapToUsers
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseRealtimeControllerUser: FirebaseRealtimeControllerUser,
    @ApplicationContext private val context:Context,
) : LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> = flow {
        emit(firebaseRealtimeControllerUser.logIn(userName,password,context).mapToUser())}

    override fun register(user: User): Flow<User> = flow {
        emit(firebaseRealtimeControllerUser.register(user,context).mapToUser())}

    override fun getListUsers(): Flow<List<User>> = flow {
        emit(firebaseRealtimeControllerUser.getListUsers(context).mapToUsers())
    }

}