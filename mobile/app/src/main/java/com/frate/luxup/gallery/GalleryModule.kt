package com.frate.luxup.gallery

import com.frate.luxup.app.App
import com.frate.luxup.gallery.mvvm.GalleryViewModel
import com.frate.luxup.gallery.repo.MediaRepository
import com.frate.luxup.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class GalleryModule {
    @Provides
    @FeatureScope
    fun provideGalleryViewModelFactory(
        app: App,
        mediaRepository: MediaRepository) = GalleryViewModel.Factory(app, mediaRepository)
}
