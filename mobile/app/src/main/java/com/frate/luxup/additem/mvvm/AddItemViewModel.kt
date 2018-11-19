package com.frate.luxup.additem.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.net.Uri
import android.view.View
import com.frate.luxup.gallery.mvvm.EXTRA_MEDIA
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.model.Article
import com.frate.luxup.mvvm.BaseViewModel
import com.frate.luxup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.io.File

class AddItemViewModel(
    application: Application,
    private val inventoryManager: InventoryManager) : BaseViewModel(application) {
    val state = AddItemState()
    private var disposables = CompositeDisposable()

    fun onTitleChanged(text: CharSequence) {
        if (text.isNotEmpty()) state.titleError.postValue(-1)
    }

    fun onPriceChanged(text: CharSequence) {
        if (text.isNotEmpty()) state.priceError.postValue(-1)
    }

    override fun onCleared() {
        disposables.dispose()
    }

    fun onPhotoResult(data: Intent?) {
        if (data != null) {
            state.photo.postValue(Uri.fromFile(data.getSerializableExtra(EXTRA_MEDIA) as File))
        }
    }

    fun onUploadClick(v: View) {
        with(state) {
            disposables.add(inventoryManager.addArticle(title.value!!, price.value!!.toFloat(), photo.value!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onUploadSuccess, ::onUploadFail))
        }
    }

    private fun onUploadSuccess(article: Article) {
        state.uploadSuccess.postValue(Event(true))
        //state.photo.postValue(Uri.parse(article.image))
    }

    private fun onUploadFail(t: Throwable) = Timber.d(t)

    class Factory(
        private val application: Application,
        private val inventoryManager: InventoryManager) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = AddItemViewModel(
            application,
            inventoryManager) as T
    }
}