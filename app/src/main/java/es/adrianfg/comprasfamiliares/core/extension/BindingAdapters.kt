package es.adrianfg.comprasfamiliares.core.extension

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import es.adrianfg.comprasfamiliares.core.base.firebaseStorage.ProjectStorageReference
import es.adrianfg.comprasfamiliares.core.base.glide.GlideApp
import javax.inject.Inject
import javax.inject.Singleton


@BindingAdapter("loadImage","placeHolder")
fun AppCompatImageView.loadImage(profileImage: String?, placeHolder: Drawable?) {
    profileImage?.let {
        val storage = Firebase.storage
        val storageRef = storage.reference
        GlideApp.with(context)
            .load(storageRef.child(profileImage))
            .defaultOptions(placeHolder)
            .into(this);
    }

}