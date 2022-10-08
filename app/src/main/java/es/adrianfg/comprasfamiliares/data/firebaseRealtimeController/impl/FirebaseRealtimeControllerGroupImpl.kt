package es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.impl

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.adrianfg.comprasfamiliares.R
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import es.adrianfg.comprasfamiliares.data.firebaseRealtimeController.FirebaseRealtimeControllerGroup
import es.adrianfg.comprasfamiliares.data.response.GroupResponseItem
import es.adrianfg.comprasfamiliares.data.response.GroupsResponse
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseRealtimeControllerGroupImpl @Inject constructor(
    private val storageImages:StorageImages
) : FirebaseRealtimeControllerGroup {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Groups")

    override suspend fun register(group: Group,imageView: AppCompatImageView, context: Context): GroupResponseItem {
        val groupResponseItem = GroupResponseItem(group.name, group.description, group.image, group.users)
        try {
            val result = database.orderByChild("name").equalTo(group.name).get().await()
            if (result.exists()) {
                throw Error(context.resources?.getString(R.string.error_groups_repeat_name))

            } else {
                storageImages.uploadStorageImage(group.image,imageView)
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
                    val groupValue =
                        group.getValue(GroupResponseItem::class.java) ?: GroupResponseItem()
                    if (groupValue.users?.contains(user.email) == true) {
                        listGroups.add(groupValue)
                    }
                }

            } else {
                throw Error(context.resources?.getString(R.string.error_groups_empty))
            }
            return GroupsResponse(listGroups.toList())

        } catch (e: Exception) {
            throw Error(e.message)
        }
    }
}