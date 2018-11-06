package com.frate.luxup.net

import com.frate.luxup.model.Article
import io.reactivex.Single
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val articleService: ArticleService) : BaseRepository() {
    fun articles(): Single<List<Article>> = articleService.articles()
        .compose(singleTransformer())

    fun addArticle(article: Article): Single<Article> = articleService.addArticle(article.toJson())
        .compose(singleTransformer())
}