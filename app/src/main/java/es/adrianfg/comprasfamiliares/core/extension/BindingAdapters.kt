package es.adrianfg.comprasfamiliares.core.extension


import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import es.adrianfg.comprasfamiliares.core.base.glide.GlideApp

@BindingAdapter("loadImage","placeHolder")
fun AppCompatImageView.loadImage(profileImage: String?,placeHolder: Drawable?) {
    profileImage?.let {
        getStorageImage(this,profileImage,placeHolder)
    }
}