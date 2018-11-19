package com.frate.luxup.profile.mvvm

import android.arch.lifecycle.MutableLiveData
import com.frate.luxup.util.Event
import com.umairjavid.kombind.model.MutableLiveField

class ProfileState {
    val userAvatar = MutableLiveField("")
    val displayName = MutableLiveField("")
    val displayNameError = MutableLiveField(-1)
    val email = MutableLiveField("")
    val emailError = MutableLiveField(-1)
    val phone = MutableLiveField("")
    val phoneError = MutableLiveField(-1)
    val finish = MutableLiveData<Event<Boolean>>()

    operator fun invoke(func: ProfileState.() -> Unit) = func()
}
