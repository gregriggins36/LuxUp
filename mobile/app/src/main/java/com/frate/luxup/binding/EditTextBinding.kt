package com.frate.luxup.binding

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout

object EditTextBinding {
    @JvmStatic
    @BindingAdapter("app:error")
    fun setError(view: TextInputLayout, errorResId: Int) {
        with(view) {
            error = if (errorResId < 0) {
                isErrorEnabled = false
                null
            } else {
                isErrorEnabled = true
                context.getString(errorResId)
            }
        }
    }
}
