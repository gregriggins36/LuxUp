package com.frate.luxup.feed.mvvm

import android.arch.lifecycle.MutableLiveData
import com.frate.luxup.model.Article
import com.frate.luxup.util.Event
import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.model.MutableLiveField

class FeedState {
    val articles = MutableLiveArrayList<Article>()
    val openAuth = MutableLiveData<Event<Boolean>>()
    val toolbarUserAvatar = MutableLiveField("")
    val toolbarTitle = "LuxUp"

    operator fun invoke(func: FeedState.() -> Unit) = func()
}
