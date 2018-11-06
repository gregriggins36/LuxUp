package com.frate.luxup.feed.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.view.View
import androidx.navigation.findNavController
import com.frate.luxup.R
import com.frate.luxup.feed.adapter.ArticleAdapter
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.model.Article
import com.frate.luxup.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FeedViewModel(
        application: Application,
        private val inventoryManager: InventoryManager) : BaseViewModel(application), ArticleAdapter.Handler {
    val state = FeedState()
    private var disposables = CompositeDisposable()

    init {
        renderItems()
    }

    private fun renderItems() {
        disposables.add(inventoryManager.articles()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onArticlesSuccess, ::onArticlesFail))
    }

    private fun onArticlesSuccess(items: List<Article>) {
        state {
            articles.clear()
            articles.addAll(items)
        }
    }

    private fun onArticlesFail(t: Throwable) = Timber.d(t)

    fun onAddArticleClick(view: View) {
        view.findNavController().navigate(R.id.addItemFragment)
    }

    override fun onArticleClick(item: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCleared() {
        disposables.dispose()
    }

    fun onUploadSuccess() {
        renderItems()
    }

    class Factory(
            private val application: Application,
            private val inventoryManager: InventoryManager
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeedViewModel(
                application,
                inventoryManager
        ) as T
    }
}
