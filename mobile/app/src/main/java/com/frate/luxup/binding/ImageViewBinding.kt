package com.frate.luxup.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object ImageViewBinding {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable) {
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUri(imageView: ImageView, uri: Uri) {
        imageView.setImageURI(uri)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrBlank()) {
            Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
        } else {
            Glide.with(view.context).clear(view)
            view.setImageDrawable(ColorDrawable(ContextCompat.getColor(view.context, android.R.color.black)))
        }
    }

    @JvmStatic
    @BindingAdapter("imageFile")
    fun setImageFile(view: ImageView, imageFile: File?) {
        if (imageFile != null) {
            Glide.with(view.context)
                    .load(imageFile)
                    .into(view)
        }
    }
}
