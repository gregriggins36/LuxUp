package com.frate.luxup.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.view.View
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.model.Article
import com.frate.luxup.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MainViewModel(
    application: Application,
    private val inventoryManager: InventoryManager) : BaseViewModel(application), ArticleAdapter.Handler {
    val state = MainState()
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

    fun onAddArticleClick(v: View) {

    }

    override fun onArticleClick(item: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCleared() {
        disposables.dispose()
    }

    class Factory(
        private val application: Application,
        private val inventoryManager: InventoryManager
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(
            application,
            inventoryManager) as T
    }
}