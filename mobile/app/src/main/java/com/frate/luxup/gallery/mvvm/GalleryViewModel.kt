package com.frate.luxup.gallery.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import androidx.core.os.bundleOf
import com.frate.luxup.R
import com.frate.luxup.gallery.adapter.GalleryAdapter
import com.frate.luxup.gallery.adapter.GalleryAdapterItem
import com.frate.luxup.gallery.adapter.GalleryItem
import com.frate.luxup.gallery.adapter.photoGalleryItem
import com.frate.luxup.gallery.repo.MediaRepository
import com.frate.luxup.mvvm.BaseViewModel
import com.frate.luxup.ui.ARG_MESSAGE_STRING_RES_ID
import com.frate.luxup.ui.ARG_POSITIVE_BUTTON_STRING_RES_ID
import com.frate.luxup.ui.SimpleDialogFragment
import com.frate.luxup.util.createImageFile
import com.umairjavid.kombind.ui.DialogFragmentBuilder
import io.reactivex.disposables.Disposables

open class GalleryViewModel(
        application: Application,
        private val mediaRepository: MediaRepository) :
        BaseViewModel(application),
        GalleryAdapter.Handler {
    val state = GalleryState()
    private var disposable = Disposables.empty()

    fun onPermissionGranted() {
        state.galleryItems.add(photoGalleryItem)
        state.galleryItems.addAll(
                mediaRepository.getAllUserPhotosAndVideos(getApplication())
                        .map { GalleryItem(it) }
        )
    }

    override fun onGalleryItemClick(item: GalleryAdapterItem) {
        if (item == photoGalleryItem) {
            onCameraSelected()
        } else if (item is GalleryItem) {
            state.mediaFile = item.photo
        }
    }

    private fun onCameraSelected() {
        state.mediaFile = createImageFile(getApplication())
        openCamera()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getApplication<Application>().packageManager) == null) {
            showDialog(DialogFragmentBuilder(
                    SimpleDialogFragment::class.java,
                    bundleOf(
                            ARG_MESSAGE_STRING_RES_ID to R.string.could_not_find_camera,
                            ARG_POSITIVE_BUTTON_STRING_RES_ID to android.R.string.ok),
                    ""))
        } else if (state.mediaFile != null) {
            val photoURI = FileProvider.getUriForFile(getApplication(),
                    getApplication<Application>().getString(R.string.content_authority),
                    state.mediaFile!!)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent, CAMERA_REQUEST)
        }
    }

    fun onCameraResult(isSuccessful: Boolean) {
        if (isSuccessful) {
            state.galleryItems.add(2, GalleryItem(state.mediaFile!!))
        } else {
            state.mediaFile?.delete()
        }
    }

    override fun onCleared() = disposable.dispose()

    @Suppress("UNCHECKED_CAST")
    class Factory(
            private val application: Application,
            private val mediaRepository: MediaRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>) = GalleryViewModel(
                application,
                mediaRepository) as T
    }
}
