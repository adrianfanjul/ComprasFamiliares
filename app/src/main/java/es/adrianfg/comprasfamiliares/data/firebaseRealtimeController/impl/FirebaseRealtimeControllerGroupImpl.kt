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
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


class FirebaseRealtimeControllerGroupImpl @Inject constructor(
    private val storageImages: StorageImages,
) : FirebaseRealtimeControllerGroup {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Groups")
    private val maxTimeout = 3000L

    override suspend fun register(group: Group,imageView: AppCompatImageView,context: Context): GroupResponseItem
    {
        val groupResponseItem = GroupResponseItem( group.name, group.description, group.image,group.users,group.createUser)
        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").equalTo(group.name).get().await()
                if (result.exists()) {
                    throw Error(context.resources?.getString(R.string.error_groups_repeat_name))

                } else {
                    storageImages.uploadStorageImage(group.image, imageView)
                    database.push().setValue(groupResponseItem)
                }
            }
            return groupResponseItem

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

    override suspend fun getListGroups(user: User, context: Context): GroupsResponse {
        try {
            return getList(user.email,context)
        } catch (e: Exception) {
            throw Error(e.message)
        }
    }

    override suspend fun deleteGroup(group: Group, context: Context): GroupsResponse {
        try {
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").equalTo(group.name).get().await()
                if (result.exists()) {
                    for (res in result.children) {
                        res.getValue(GroupResponseItem::class.java) ?: GroupResponseItem()
                        storageImages.deleteStorageImage(group.image)
                        res.key?.let { database.child(it).removeValue() }
                    }
                } else {
                    throw Error(context.resources?.getString(R.string.error_group_not_found))
                }
            }
            return getList(group.createUser,context)

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }

    private suspend fun getList(email: String,context: Context): GroupsResponse {
        try {
            val listGroups = mutableListOf<GroupResponseItem>()
            withTimeout(maxTimeout) {
                val result = database.orderByChild("name").get().await()
                if (result.exists()) {
                    for (group in result.children) {
                        val groupValue =
                            group.getValue(GroupResponseItem::class.java) ?: GroupResponseItem()
                        if (groupValue.users?.contains(email) == true) {
                            listGroups.add(groupValue)
                        }
                    }
                }
            }
            return GroupsResponse(listGroups.toList())

        } catch (ex: Exception) {
            throw Error(context.resources?.getString(R.string.error_time_out))
        }
    }
}