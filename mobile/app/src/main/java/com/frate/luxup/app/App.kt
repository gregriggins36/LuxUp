package com.frate.luxup.app

import android.app.Application

class App : Application() {
    companion object {
        lateinit var app: App

        private var librariesInitialized = false
        fun librariesInitialized() =
                if (librariesInitialized) {
                    true
                } else {
                    librariesInitialized = true
                    false
                }
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        component.inject(this)
    }
}
