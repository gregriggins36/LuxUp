package com.frate.luxup.additem.mvvm

import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import com.frate.luxup.util.Event
import com.umairjavid.kombind.model.MutableLiveField

class AddItemState {
    val title = MutableLiveField("")
    val titleError = MutableLiveField(-1)
    val price = MutableLiveField("")
    val priceError = MutableLiveField(-1)
    val photo = MutableLiveField(Uri.EMPTY)
    val uploadSuccess = MutableLiveData<Event<Boolean>>()

    operator fun invoke(func: AddItemState.() -> Unit) = func()
}