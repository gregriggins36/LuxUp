package com.frate.luxup.profile

import com.frate.luxup.app.App
import com.frate.luxup.manager.UserManager
import com.frate.luxup.profile.mvvm.ProfileViewModel
import com.frate.luxup.scope.FeatureScope
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {
    @Provides
    @FeatureScope
    fun provideProfileViewModelFactory(
        app: App,
        userManager: UserManager) =
        ProfileViewModel.Factory(app, userManager)
}
