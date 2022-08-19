package es.adrianfg.comprasfamiliares.data.repository
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adrianfg.comprasfamiliares.data.datasource.LoginDatasource
import es.adrianfg.comprasfamiliares.data.mappers.mapToUser
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDatasource: LoginDatasource,
    @ApplicationContext private val context:Context,
) : LoginRepository {

    override fun logIn(userName: String, password: String): Flow<User> =
        loginDatasource.logIn(userName,password).map {it.mapToUser()}

}