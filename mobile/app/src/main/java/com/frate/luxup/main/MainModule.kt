package com.frate.luxup.main

import com.frate.luxup.BASE_WEB_URL
import com.frate.luxup.app.App
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.net.ArticleService
import com.frate.luxup.net.RestClient
import com.frate.luxup.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    @FeatureScope
    fun provideMainViewModelFactory(
        app: App,
        inventoryManager: InventoryManager) =
        MainViewModel.Factory(app, inventoryManager)

    @Provides
    fun articleService(restClient: RestClient): ArticleService =
        restClient.createRetrofitAdapter(BASE_WEB_URL).create(ArticleService::class.java)
}