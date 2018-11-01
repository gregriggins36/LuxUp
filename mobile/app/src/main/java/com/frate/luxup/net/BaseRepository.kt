package com.frate.luxup.net

import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers

open class BaseRepository {
    fun <T> singleTransformer(): SingleTransformer<T, T> = SingleTransformer {
            upstream -> upstream.subscribeOn(Schedulers.io())
    }

    fun completableTransformer(): CompletableTransformer = CompletableTransformer {
        upstream -> upstream.subscribeOn(Schedulers.io())
    }
}
