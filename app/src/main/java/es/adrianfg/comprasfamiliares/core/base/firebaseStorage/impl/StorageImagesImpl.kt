package es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.ProjectStorageReference
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class StorageImagesImpl @Inject constructor(private val projectStorageReference: ProjectStorageReference) : StorageImages {
    private val storageRef = projectStorageReference.getStorageReference()

    //Se le pasa el pathstring y el imageview y sube la foto al storage
    override suspend fun uploadStorageImage(pathString: String,imageView: AppCompatImageView) {
        val imagesRef = storageRef.child(pathString)
        // Get the data from an ImageView as bytes
        val bitmap = convertViewToDrawable(imageView)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val data = baos.toByteArray()
        imagesRef.putBytes(data).await()
    }

    //Con una referencia  borra la imagen del storage
    override fun deleteStorageImage(imageReference:String){
        val imagesRef = storageRef.child(imageReference)
        imagesRef.delete()
    }

    //Con una referencia  borra las imagenes del storage
    override fun deleteAllStorageImages(imageReference:String){
        val imagesRef = storageRef.child(imageReference)
        imagesRef.listAll().addOnSuccessListener {
            for (imagen in it.items){
                imagen.delete()
            }
        }
    }

    //Comvierte el imageview en un bitmap
    private fun convertViewToDrawable(view: View): Bitmap {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(spec, spec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(canvas)
        return bitmap
    }

}