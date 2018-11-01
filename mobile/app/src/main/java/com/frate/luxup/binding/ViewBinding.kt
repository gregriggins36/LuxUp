package com.frate.luxup.binding

import android.databinding.BindingAdapter
import android.view.View

object ViewBinding {
    @JvmStatic
    @BindingAdapter("gone")
    fun setVisibility(view: View, beGone: Boolean) {
        view.visibility = if (beGone) View.GONE else View.VISIBLE
    }
}
