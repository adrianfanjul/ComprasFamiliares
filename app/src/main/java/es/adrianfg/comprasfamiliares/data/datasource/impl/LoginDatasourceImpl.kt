package es.adrianfg.comprasfamiliares.data.datasource.impl

import es.adrianfg.comprasfamiliares.data.datasource.LoginDatasource
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.FirebaseRealtimeDAO
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeDAO.impl.FirebaseRealtimeDAOImpl
import es.adrianfg.comprasfamiliares.data.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginDatasourceImpl @Inject constructor(
    private val firebaseRealtimeDAO: FirebaseRealtimeDAO

): LoginDatasource {
    override fun logIn(user:String,pass:String): Flow<UserResponse> = flow {
        emit(firebaseRealtimeDAO.logIn(user, pass))
    }


    override fun registerUser() {
        //Not yet implemented
    }

    override fun logOut() {
        //Not yet implemented
    }
}