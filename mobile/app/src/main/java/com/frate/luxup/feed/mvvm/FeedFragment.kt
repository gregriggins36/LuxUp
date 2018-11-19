package com.frate.luxup.feed.mvvm

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.frate.luxup.R
import com.frate.luxup.app.App.Companion.app
import com.frate.luxup.feed.DaggerFeedComponent
import com.frate.luxup.feed.FeedComponent
import com.frate.luxup.feed.adapter.ArticleAdapter
import com.frate.luxup.main.MainViewModel
import com.frate.luxup.mvvm.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed.*

private const val RC_SIGN_IN = 123

class FeedFragment : BaseFragment<FeedViewModel, FeedViewModel.Factory, FeedComponent>() {
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    override val viewModelClass = FeedViewModel::class.java
    override val layoutResId = R.layout.fragment_feed

    override fun createComponent(): FeedComponent = DaggerFeedComponent
        .builder()
        .appComponent(app.component)
        .build()
        .also { it.inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        add_article.setOnClickListener { viewModel.onAddArticleClick(it) }

        setUploadListener()
        setAuthListener()
    }

    private fun setAuthListener() {
        viewModel.state.openAuth.observe(this, Observer { event ->
            event?.getContentIfNotHandled {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                    RC_SIGN_IN)
            }
        })
    }

    private fun setUploadListener() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                viewModel.onAuthSuccess()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                if (response != null) {
                    Toast.makeText(
                        context,
                        "Sign in failed with error ${response.error?.errorCode}",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
