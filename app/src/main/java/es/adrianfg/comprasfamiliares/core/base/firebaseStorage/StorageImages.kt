package es.adrianfg.comprasfamiliares.core.base.firebaseStorage

import androidx.appcompat.widget.AppCompatImageView


interface StorageImages {
    suspend fun  uploadStorageImage(pathString: String,imageView: AppCompatImageView)
    fun deleteStorageImage(imageReference:String)
    fun deleteAllStorageImages(imageReference:String)
}
