package com.frate.luxup.main

import com.frate.luxup.app.App
import com.frate.luxup.app.AppComponent
import com.frate.luxup.net.NetworkModule
import com.frate.luxup.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainModule::class, NetworkModule::class]
)
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    val app: App
}
