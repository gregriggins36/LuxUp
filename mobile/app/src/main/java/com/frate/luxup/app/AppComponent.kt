package com.frate.luxup.app

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class
        ]
)
interface AppComponent {
    fun inject(app: App)

    val app: App
}
