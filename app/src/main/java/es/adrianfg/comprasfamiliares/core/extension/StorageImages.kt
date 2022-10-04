package es.adrianfg.comprasfamiliares.core.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.BuildConfig
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File

//Se le pasa el pathstring y el imageview y sube la foto al storage
suspend fun uploadStorageImage(pathString: String,imageView: AppCompatImageView) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child(pathString)
    // Get the data from an ImageView as bytes
    val bitmap = convertViewToDrawable(imageView)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    val data = baos.toByteArray()
    imagesRef.putBytes(data).await()
}
//Con una referencia coloca la imagen del storage en el imageview
suspend fun deleteStorageImage(imageReference:String){
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child(imageReference)
    imagesRef.delete().await()
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

//Crea una imagen temporal
fun getTmpFileUri(context: Context): Uri {
    val tmpFile = File.createTempFile("tmp_image_file", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(context,"${BuildConfig.APPLICATION_ID}.provider",tmpFile)
}

