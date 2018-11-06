package com.frate.luxup.main

import android.os.Bundle
import com.frate.luxup.R
import com.frate.luxup.ext.app
import com.frate.luxup.mvvm.BaseActivity

class MainActivity : BaseActivity<MainViewModel, MainViewModel.Factory, MainComponent>() {
    override val layoutResId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class.java

    override fun createComponent(): MainComponent = DaggerMainComponent.builder()
        .appComponent(app.component)
        .mainModule(MainModule())
        .build()
        .also { it.inject(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel.refreshFeed.observe(this, Observer { event ->
//            event?.getContentIfNotHandled {
//                viewModel.refreshFeed.postValue(Event(false))
//            }
//        })
    }
}
