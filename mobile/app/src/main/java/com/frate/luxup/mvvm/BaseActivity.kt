package com.frate.luxup.mvvm

import android.arch.lifecycle.ViewModelProvider
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.frate.luxup.BuildConfig
import com.frate.luxup.app.App
import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombind.ui.KombindViewModel
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

abstract class BaseActivity<VM : KombindViewModel, VMF : ViewModelProvider.Factory, C : Any> : KombindActivity<VM>() {
    @Inject
    protected lateinit var viewModelFactory: VMF
    lateinit var component: C

    override fun onBeforeViewLoad(savedInstanceState: Bundle?) {
        if (!App.librariesInitialized()) {
            setupLogging()
            setupRxJava2ErrorHandling()
            setupStrictMode()
        }
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskWrites()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }
    }

    abstract fun createComponent(): C

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        component = createComponent()
        return viewModelFactory
    }

    private fun setupLogging() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupRxJava2ErrorHandling() {
        RxJavaPlugins.setErrorHandler {
            var e: Throwable? = it
            if (e is UndeliverableException) {
                e = e.cause
            }
            if ((e is IOException) || (e is SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if ((e is NullPointerException) || (e is IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                        .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                        .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            Timber.d("Undeliverable exception received, not sure what to do", e)
        }
    }

    protected fun allPermissionsGranted(permissions: List<String>) =
            permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }

    protected fun getRuntimePermissions(permissions: List<String>, permissionRequestCode: Int) {
        val allNeededPermissions = mutableListOf<String>()
        if (!allPermissionsGranted(permissions)) {
            allNeededPermissions.addAll(permissions.filter {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            })
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, allNeededPermissions.toTypedArray(), permissionRequestCode)
        }
    }
}
