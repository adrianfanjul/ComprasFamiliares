package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl

import android.content.Context
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerGroup
import es.adrianfg.comprasfamiliares.data.response.GroupResponseItem
import es.adrianfg.comprasfamiliares.data.response.GroupsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseRealtimeControllerGroupImpl @Inject constructor() : FirebaseRealtimeControllerGroup {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Groups")

    override suspend fun register(group: Group, context: Context): GroupResponseItem {
        val groupResponseItem = GroupResponseItem(group.name, group.description, group.image, group.users)
        try {
            val result = database.orderByChild("name").equalTo(group.name).get().await()
            if (result.exists()) {
                throw Error(context.resources?.getString(R.string.error_groups_repeat_name))

            } else {
                database.push().setValue(groupResponseItem)
                return groupResponseItem
            }

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }

    override suspend fun getListGroups(user: User, context: Context): GroupsResponse {

        val listGroups = mutableListOf<GroupResponseItem>()

        try {
            val result = database.orderByChild("name").get().await()
            if (result.exists()) {
                for (group in result.children) {
                    listGroups.add(group.getValue(GroupResponseItem::class.java) ?: GroupResponseItem())
                }
                Log.e("firebase", listGroups.toString())

            } else {
                throw Error(context.resources?.getString(R.string.error_groups_empty))
            }
            return GroupsResponse(listGroups.toList())

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }
}