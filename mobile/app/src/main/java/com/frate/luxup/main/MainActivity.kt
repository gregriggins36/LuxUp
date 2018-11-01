package com.frate.luxup.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.frate.luxup.R
import com.frate.luxup.ext.app
import com.frate.luxup.mvvm.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, MainViewModel.Factory, MainComponent>() {
    override val layoutResId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class.java

    override fun createComponent(): MainComponent {
        val component = DaggerMainComponent.builder()
            .appComponent(app.component)
            .mainModule(MainModule())
            .build()
        component.inject(this)
        return component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupAdapter()
    }

    private fun setupAdapter() = with(articles) {
        val adapter = ArticleAdapter(viewModel.state.articles, viewModel)
        adapter.registerObserver(this@MainActivity)
        layoutManager = android.support.v7.widget.LinearLayoutManager(this@MainActivity)
        this.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
