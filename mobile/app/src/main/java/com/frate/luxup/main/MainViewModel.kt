package com.frate.luxup.main

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.frate.luxup.mvvm.BaseViewModel
import com.frate.luxup.util.Event

open class MainViewModel(application: Application) : BaseViewModel(application){
    val onUploadSuccess = MutableLiveData<Event<Boolean>>()

    class Factory(
        private val application: Application) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(application) as T
    }
}