package es.adrianfg.comprasfamiliares.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerGroup
import es.adrianfg.comprasfamiliares.data.mappers.mapToGroup
import es.adrianfg.comprasfamiliares.data.mappers.mapToGroups
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import es.adrianfg.comprasfamiliares.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    private val firebaseRealtimeControllerGroup: FirebaseRealtimeControllerGroup,
    @ApplicationContext private val context: Context,
) : GroupsRepository {

    override fun getListGroups(user:User): Flow<List<Group>> = flow {
        emit(firebaseRealtimeControllerGroup.getListGroups(user,context).mapToGroups())
    }

    override fun registerGroup(group: Group): Flow<Group> = flow {
        emit(firebaseRealtimeControllerGroup.register(group,context).mapToGroup())
    }


}