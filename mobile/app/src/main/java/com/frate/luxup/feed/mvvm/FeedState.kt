package com.frate.luxup.feed.mvvm

import com.frate.luxup.model.Article
import com.umairjavid.kombind.model.MutableLiveArrayList

class FeedState {
    val articles = MutableLiveArrayList<Article>()

    operator fun invoke(func: FeedState.() -> Unit) = func()
}
