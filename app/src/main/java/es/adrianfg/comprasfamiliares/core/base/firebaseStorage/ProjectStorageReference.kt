package es.adrianfg.comprasfamiliares.core.base.firebaseStorage

import com.google.firebase.storage.StorageReference

interface ProjectStorageReference {
    fun getStorageReference():StorageReference
}