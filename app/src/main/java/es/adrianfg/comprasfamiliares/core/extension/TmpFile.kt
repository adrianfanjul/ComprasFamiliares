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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.io.File

//Crea una imagen temporal
fun getTmpFileUri(context: Context): Uri {
    val tmpFile = File.createTempFile("tmp_image_file", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(context,"${BuildConfig.APPLICATION_ID}.provider",tmpFile)
}

