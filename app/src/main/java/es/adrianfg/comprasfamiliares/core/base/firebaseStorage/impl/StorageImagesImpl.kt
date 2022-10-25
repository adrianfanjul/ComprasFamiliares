package es.adrianfg.comprasfamiliares.core.base.firebaseStorage.impl

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.StorageImages
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class StorageImagesImpl @Inject constructor() : StorageImages {

    //Se le pasa el pathstring y el imageview y sube la foto al storage
    override suspend fun uploadStorageImage(pathString: String, imageView: AppCompatImageView) {
        // Get the data from an ImageView as bytes
        val bitmap = convertViewToDrawable(imageView)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val data = baos.toByteArray()
        Firebase.storage.reference.child(pathString).putBytes(data).await()
    }

    //Con una referencia  borra la imagen del storage
    override fun deleteStorageImage(imageReference: String) {
        Firebase.storage.reference.child(imageReference).delete()
    }

    //Con una referencia  borra las imagenes del storage
    override fun deleteAllStorageImages(imageReference: String) {
        Firebase.storage.reference.child(imageReference).listAll()
            .addOnSuccessListener {
                for (imagen in it.items) {
                    imagen.delete()
                }
            }
    }

    //Comvierte el imageview en un bitmap
    private fun convertViewToDrawable(view: View): Bitmap {
        val spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(spec, spec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.translate((-view.scrollX).toFloat(), (-view.scrollY).toFloat())
        view.draw(canvas)
        return bitmap
    }

}