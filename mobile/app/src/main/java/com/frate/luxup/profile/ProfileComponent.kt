package com.frate.luxup.profile

import com.frate.luxup.app.App
import com.frate.luxup.app.AppComponent
import com.frate.luxup.profile.mvvm.ProfileFragment
import com.frate.luxup.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ProfileModule::class]
)
interface ProfileComponent {
    fun inject(profileFragment: ProfileFragment)

    val app: App
}
