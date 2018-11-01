package com.frate.luxup.ext

import com.umairjavid.kombind.ui.KombindFragment

val KombindFragment<*>.app
    get() = activity!!.app
