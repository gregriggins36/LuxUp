package com.frate.luxup.feed.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.view.View
import androidx.navigation.findNavController
import com.frate.luxup.R
import com.frate.luxup.feed.adapter.ArticleAdapter
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.manager.UserManager
import com.frate.luxup.model.Article
import com.frate.luxup.mvvm.BaseViewModel
import com.frate.luxup.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FeedViewModel(
        application: Application,
        private val inventoryManager: InventoryManager,
        private val userManager: UserManager) : BaseViewModel(application), ArticleAdapter.Handler {
    val state = FeedState()
    private var disposables = CompositeDisposable()

    init {
        if (userManager.isUserLoggedIn()) state.toolbarUserAvatar.postValue(userManager.avatar())
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
        if (userManager.isUserLoggedIn()) {
            view.findNavController().navigate(R.id.addItemFragment)
        } else {
            state.openAuth.postValue(Event(true))
        }
    }

    override fun onArticleClick(item: Article) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onUploadSuccess() {
        renderItems()
    }

    fun onAuthSuccess() {
        userManager.onAuthSuccess()
        state.toolbarUserAvatar.postValue(userManager.avatar())
    }

    fun onProfileClick(v: View) {
        if (userManager.isUserLoggedIn()) {
            v.findNavController().navigate(R.id.profileFragment)
        } else {
            state.openAuth.postValue(Event(true))
        }
    }

    override fun onCleared() {
        disposables.dispose()
    }

    class Factory(
            private val application: Application,
            private val inventoryManager: InventoryManager,
            private val userManager: UserManager
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = FeedViewModel(
                application,
                inventoryManager,
                userManager
        ) as T
    }
}
