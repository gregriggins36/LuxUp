package com.frate.luxup.feed

import com.frate.luxup.app.App
import com.frate.luxup.app.AppComponent
import com.frate.luxup.feed.mvvm.FeedFragment
import com.frate.luxup.net.NetworkModule
import com.frate.luxup.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
        dependencies = [AppComponent::class],
        modules = [FeedModule::class, NetworkModule::class]
)
interface FeedComponent {
    fun inject(feedFragment: FeedFragment)

    val app: App
}
