package com.frate.luxup.profile.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.frate.luxup.R
import com.frate.luxup.manager.UserManager
import com.frate.luxup.mvvm.BaseViewModel
import com.frate.luxup.util.Event

class ProfileViewModel(
    application: Application,
    private val userManager: UserManager) : BaseViewModel(application) {
    val state = ProfileState()

    init {
        with(state) {
            userAvatar.postValue(userManager.avatar())
            displayName.postValue(userManager.name())
            email.postValue(userManager.email())
            phone.postValue(userManager.phoneNumber())
        }
    }

    fun onNameChanged(text: CharSequence) {
        if (text.isNotEmpty()) state.displayNameError.postValue(-1)
    }

    fun onEmailChanged(text: CharSequence) {
        if (text.isNotEmpty()) state.emailError.postValue(-1)
    }

    fun onPhoneChanged(text: CharSequence) {
        if (text.isNotEmpty()) state.phoneError.postValue(-1)
    }

    fun onSignOutClick() {
        AuthUI.getInstance()
            .signOut(getApplication())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userManager.signOut()
                    state.finish.postValue(Event(true))
                } else {
                    Toast.makeText(getApplication(), R.string.sign_out_failed, Toast.LENGTH_LONG).show()
                }
            }
    }

    class Factory(
        private val application: Application,
        private val userManager: UserManager
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = ProfileViewModel(
            application,
            userManager) as T
    }
}
