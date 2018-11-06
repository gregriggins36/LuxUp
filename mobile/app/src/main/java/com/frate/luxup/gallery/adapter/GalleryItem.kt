package com.frate.luxup.gallery.adapter

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.frate.luxup.R
import java.io.File

sealed class GalleryAdapterItem

class GalleryItem(
        val photo: File
) : GalleryAdapterItem()

class GalleryIconItem(
        @DrawableRes val drawableResId: Int,
        @StringRes val text: Int) : GalleryAdapterItem()

val photoGalleryItem = GalleryIconItem(R.drawable.ic_photo_camera, R.string.take_a_photo)
