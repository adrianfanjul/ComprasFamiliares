package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.GroupsRepository
import es.adrianfg.comprasfamiliares.domain.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListGroupsUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val groupsRepository: GroupsRepository
) : FlowUseCase<GetListGroupsUseCase.Params, List<Group>>(dispatcher) {

    override fun execute(params: Params): Flow<List<Group>> = groupsRepository.getListGroups(params.user)

    data class Params(val user:User)

}