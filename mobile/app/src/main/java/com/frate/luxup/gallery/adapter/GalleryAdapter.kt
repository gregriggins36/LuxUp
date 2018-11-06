package com.frate.luxup.gallery.adapter

import com.frate.luxup.R
import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.ui.KombindAdapter

open class GalleryAdapter(items: MutableLiveArrayList<GalleryAdapterItem>, val handler: Any?) :
        KombindAdapter<KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = if (items[position] is GalleryIconItem) R.layout.item_gallery_icon else R.layout.item_gallery

    override fun getHandler(position: Int) = handler

    interface Handler {
        fun onGalleryItemClick(item: GalleryAdapterItem)
    }
}
