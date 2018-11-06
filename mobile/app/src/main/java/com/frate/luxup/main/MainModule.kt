package com.frate.luxup.main

import com.frate.luxup.app.App
import com.frate.luxup.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    @FeatureScope
    fun provideMainViewModelFactory(app: App) = MainViewModel.Factory(app)
}