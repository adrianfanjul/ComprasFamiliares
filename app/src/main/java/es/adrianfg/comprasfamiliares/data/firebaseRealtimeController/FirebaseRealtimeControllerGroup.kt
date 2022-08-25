package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController

import android.content.Context
import es.adrianfg.comprasfamiliares.data.response.GroupResponseItem
import es.adrianfg.comprasfamiliares.data.response.GroupsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User

interface FirebaseRealtimeControllerGroup {
    suspend fun register(group: Group,context: Context): GroupResponseItem
    suspend fun getListGroups(user: User,context: Context): GroupsResponse
}