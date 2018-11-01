package com.frate.luxup.net

import com.frate.luxup.model.Article
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ArticleService {
    @GET("/articles")
    fun articles() : Single<List<Article>>

    @POST("/articles")
    fun addArticle(@Body articleJson: JsonObject) : Completable
}