package es.adrianfg.comprasfamiliares.domain.repository

import androidx.appcompat.widget.AppCompatImageView
import es.adrianfg.comprasfamiliares.domain.models.Group
import es.adrianfg.comprasfamiliares.domain.models.User
import kotlinx.coroutines.flow.Flow


interface GroupsRepository {
    fun getListGroups(user: User): Flow<List<Group>>
    fun registerGroup(group: Group,imageView: AppCompatImageView): Flow<Group>
}

