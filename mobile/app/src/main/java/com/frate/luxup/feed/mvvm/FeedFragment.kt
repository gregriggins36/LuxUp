package com.frate.luxup.feed.mvvm

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.frate.luxup.R
import com.frate.luxup.app.App.Companion.app
import com.frate.luxup.feed.DaggerFeedComponent
import com.frate.luxup.feed.FeedComponent
import com.frate.luxup.feed.FeedModule
import com.frate.luxup.feed.adapter.ArticleAdapter
import com.frate.luxup.main.MainViewModel
import com.frate.luxup.mvvm.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : BaseFragment<FeedViewModel, FeedViewModel.Factory, FeedComponent>() {
    override val viewModelClass = FeedViewModel::class.java
    override val layoutResId = R.layout.fragment_feed

    override fun createComponent(): FeedComponent = DaggerFeedComponent
        .builder()
        .appComponent(app.component)
        .feedModule(FeedModule())
        .build()
        .also { it.inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
            supportActionBar?.title = "LuxUp"
        }
        setupAdapter()

        add_article.setOnClickListener { viewModel.onAddArticleClick(it) }

        parentViewModel(MainViewModel::class.java).onUploadSuccess.observe(this, Observer { event ->
            event?.getContentIfNotHandled {
                viewModel.onUploadSuccess()
            }
        })
    }

    private fun setupAdapter() = with(articles) {
        val adapter = ArticleAdapter(viewModel.state.articles, viewModel)
        adapter.registerObserver(this@FeedFragment)
        layoutManager = LinearLayoutManager(context)
        this.adapter = adapter
    }
}
