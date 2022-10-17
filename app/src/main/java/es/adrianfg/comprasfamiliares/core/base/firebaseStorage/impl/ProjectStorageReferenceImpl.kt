package es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.ProjectStorageReference
import javax.inject.Inject


class ProjectStorageReferenceImpl @Inject constructor(

) : ProjectStorageReference {
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    override fun getStorageReference(): StorageReference {
       return storageRef
    }

}