package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRegisterUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val loginRepository: LoginRepository
) : FlowUseCase<SetRegisterUseCase.Params, User>(dispatcher) {

    override fun execute(params: Params): Flow<User> = loginRepository.register(params.user)

    data class Params(val user: User)

}