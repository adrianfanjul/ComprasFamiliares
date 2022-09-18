package es.adrianfg.comprasfamiliares.core.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

//Se le pasa el pathstring y el imageview y sube la foto al storage
fun ImageView.uploadImage(pathString: String) {
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child(pathString)
    // Get the data from an ImageView as bytes
    val bitmap = convertViewToDrawable(this)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    val data = baos.toByteArray()
    val uploadTask = imagesRef.putBytes(data)

    uploadTask.addOnFailureListener {
        Log.e("storage","Error al subir la imagen: ${it.message}")
    }.addOnSuccessListener { taskSnapshot ->
        Log.e("storage","Subida la imagen a: $pathString")
    }
}

//Con una referencia coloca la imagen del storage en el imageview
fun getStorageImage(imageView:ImageView,imageReference:String,placeHolder: Drawable?){
    val storage = Firebase.storage
    val storageRef = storage.reference
    val imagesRef = storageRef.child(imageReference)
    val TWO_MEGABYTE: Long = 2048 * 2048

    imagesRef.getBytes(TWO_MEGABYTE).addOnSuccessListener {
        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
        imageView.setImageBitmap(bitmap)
    }.addOnFailureListener {
        imageView.setImageDrawable(placeHolder)
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

