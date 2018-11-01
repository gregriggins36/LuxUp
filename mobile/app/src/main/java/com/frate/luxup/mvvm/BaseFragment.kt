package com.frate.luxup.mvvm

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.umairjavid.kombind.ui.KombindFragment
import com.umairjavid.kombind.ui.KombindViewModel
import javax.inject.Inject

abstract class BaseFragment<VM : KombindViewModel, VMF : ViewModelProvider.Factory, C : Any> : KombindFragment<VM>() {
    @Inject
    protected lateinit var viewModelFactory: VMF
    lateinit var component: C

    abstract fun createComponent(): C

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        component = createComponent()
        return viewModelFactory
    }

    protected fun allPermissionsGranted(permissions: List<String>) =
            permissions.all { ContextCompat.checkSelfPermission(context!!, it) == PackageManager.PERMISSION_GRANTED }

    protected fun getRuntimePermissions(permissions: List<String>, permissionRequestCode: Int) {
        val allNeededPermissions = mutableListOf<String>()
        if (!allPermissionsGranted(permissions)) {
            allNeededPermissions.addAll(permissions.filter {
                ContextCompat.checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED
            })
        }

        if (!allNeededPermissions.isEmpty()) {
            requestPermissions(allNeededPermissions.toTypedArray(), permissionRequestCode)
        }
    }

    protected fun <PVM : BaseViewModel> parentViewModel(clazz: Class<PVM>): PVM {
        if (parentFragment == null) throw RuntimeException("This fragment does not have any parent fragment!")
        return if (parentFragment is NavHostFragment) {
            ViewModelProviders.of(parentFragment!!.parentFragment!!).get(clazz)
        } else {
            ViewModelProviders.of(parentFragment!!).get(clazz)
        }
    }
}
