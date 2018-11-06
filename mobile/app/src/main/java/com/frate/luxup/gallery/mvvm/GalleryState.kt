package com.frate.luxup.gallery.mvvm

import com.frate.luxup.gallery.adapter.GalleryAdapterItem
import com.umairjavid.kombind.model.MutableLiveArrayList
import java.io.File

class GalleryState {
    val galleryItems = MutableLiveArrayList<GalleryAdapterItem>()
    var mediaFile: File? = null
}
