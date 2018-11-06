package com.frate.luxup.additem

import com.frate.luxup.BASE_WEB_URL
import com.frate.luxup.CLOUD_STORAGE_BUCKET
import com.frate.luxup.additem.mvvm.AddItemViewModel
import com.frate.luxup.app.App
import com.frate.luxup.manager.InventoryManager
import com.frate.luxup.net.ArticleRepository
import com.frate.luxup.net.ArticleService
import com.frate.luxup.net.RestClient
import com.frate.luxup.scope.FeatureScope
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class AddItemModule {
    @Provides
    @FeatureScope
    fun provideAddItemViewModelFactory(app: App, inventoryManager: InventoryManager) =
        AddItemViewModel.Factory(app, inventoryManager)

    @Provides
    @FeatureScope
    fun articleService(restClient: RestClient): ArticleService =
        restClient.createRetrofitAdapter(BASE_WEB_URL).create(ArticleService::class.java)

    @Provides
    @FeatureScope
    fun inventoryManager(articleRepository: ArticleRepository): InventoryManager = InventoryManager(
        articleRepository,
        FirebaseStorage.getInstance(CLOUD_STORAGE_BUCKET)
    )
}
