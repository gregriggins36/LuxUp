package com.frate.luxup.net

import com.frate.luxup.model.Article
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val articleService: ArticleService) : BaseRepository() {
    fun articles(): Single<List<Article>> = articleService.articles()
        .compose(singleTransformer())

    fun addArticle(): Completable = articleService.addArticle()
        .compose(completableTransformer())
}