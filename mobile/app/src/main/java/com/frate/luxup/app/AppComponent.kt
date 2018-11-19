package com.frate.luxup.app

import com.frate.luxup.repository.RepositoryModule
import com.frate.luxup.repository.SharedPreferencesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            RepositoryModule::class
        ]
)
interface AppComponent {
    fun inject(app: App)

    val app: App

    val sharedPreferencesRepository: SharedPreferencesRepository
}
