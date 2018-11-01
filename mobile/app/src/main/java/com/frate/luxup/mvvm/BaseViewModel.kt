package com.frate.luxup.mvvm

import android.app.Application
import android.widget.Toast
import com.umairjavid.kombind.ui.KombindViewModel

abstract class BaseViewModel(application: Application) : KombindViewModel(application) {
    fun notYetImplemented() = Toast.makeText(getApplication(), "Not Yet Implemented", Toast.LENGTH_SHORT).show()
}
