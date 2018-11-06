package com.frate.luxup.additem

import com.frate.luxup.additem.mvvm.AddItemFragment
import com.frate.luxup.app.App
import com.frate.luxup.app.AppComponent
import com.frate.luxup.net.NetworkModule
import com.frate.luxup.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [AddItemModule::class, NetworkModule::class]
)
interface AddItemComponent {
    fun inject(addItemFragment: AddItemFragment)

    val app: App
}
