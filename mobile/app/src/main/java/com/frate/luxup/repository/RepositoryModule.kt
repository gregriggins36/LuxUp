package com.frate.luxup.repository

import android.content.Context
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(val context: Context) {
    @Singleton
    @Provides
    fun sharedPreferencesRepository() = SharedPreferencesRepository(PreferenceManager.getDefaultSharedPreferences(context))
}