package com.frate.luxup.ext

import android.app.Activity
import com.frate.luxup.app.App

val Activity.app
    get() = application as App
