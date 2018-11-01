package com.frate.luxup.manager

import com.frate.luxup.model.Article
import com.frate.luxup.net.ArticleRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class InventoryManager @Inject constructor(private val articleRepository: ArticleRepository) {
    fun articles(): Single<List<Article>> = articleRepository.articles()

    fun addArticle(article: Article): Completable = articleRepository.addArticle(article)
}
