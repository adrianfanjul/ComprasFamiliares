package es.adrianfg.comprasfamiliares.domain.usecase

import es.adrianfg.comprasfamiliares.core.di.domain.di.IoDispatcher
import es.adrianfg.comprasfamiliares.domain.core.base.FlowUseCase
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val groupsRepository: GroupsRepository,
) : FlowUseCase<DeleteGroupUseCase.Params, List<Group>>(dispatcher) {

    override fun execute(params: Params):Flow<List<Group>> {
       return groupsRepository.deleteGroup(params.group)
    }

    data class Params(val group: Group)

}