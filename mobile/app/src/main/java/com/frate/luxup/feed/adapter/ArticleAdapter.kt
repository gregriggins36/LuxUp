package com.frate.luxup.feed.adapter

import com.frate.luxup.R
import com.frate.luxup.model.Article
import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.ui.KombindAdapter

class ArticleAdapter(items: MutableLiveArrayList<Article>, val handler: Any?) : KombindAdapter<KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = R.layout.item_article

    override fun getHandler(position: Int) = handler

    interface Handler {
        fun onArticleClick(item: Article)
    }
}