package com.frate.luxup.gallery

import com.frate.luxup.app.App
import com.frate.luxup.app.AppComponent
import com.frate.luxup.gallery.mvvm.GalleryActivity
import com.frate.luxup.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
        dependencies = [AppComponent::class],
        modules = [GalleryModule::class]
)
interface GalleryComponent {
    fun inject(galleryActivity: GalleryActivity)

    val app: App
}
