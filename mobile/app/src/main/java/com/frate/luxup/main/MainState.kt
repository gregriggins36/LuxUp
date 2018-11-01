package com.frate.luxup.main

import com.frate.luxup.model.Article
import com.umairjavid.kombind.model.MutableLiveArrayList

class MainState {
    val articles = MutableLiveArrayList<Article>()

    operator fun invoke(func: MainState.() -> Unit) = func()
}
