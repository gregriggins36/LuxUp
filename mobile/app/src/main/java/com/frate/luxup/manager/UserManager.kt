package com.frate.luxup.manager

import com.frate.luxup.repository.SharedPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserManager @Inject constructor(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    fun isUserLoggedIn() = !sharedPreferencesRepository.userId().isBlank()

    fun onAuthSuccess() =
        with(FirebaseAuth.getInstance().currentUser!!) {
            sharedPreferencesRepository.setUserId(uid)
            if (email != null) sharedPreferencesRepository.setUserEmail(email!!)
            if (displayName != null) sharedPreferencesRepository.setUserName(displayName!!)
            if (photoUrl != null) sharedPreferencesRepository.setUserPhoto(photoUrl.toString())
            if (phoneNumber != null) sharedPreferencesRepository.setPhoneNumber(phoneNumber!!)
        }

    fun signOut() = with(sharedPreferencesRepository) {
            removeUserId()
            removeUserEmail()
            removeUserName()
            removeUserPhoto()
            removeUserPhoneNumber()
        }

    fun name() = sharedPreferencesRepository.userName()
    fun email() = sharedPreferencesRepository.userEmail()
    fun avatar() = sharedPreferencesRepository.userPhoto()
    fun phoneNumber() = sharedPreferencesRepository.userPhoneNumber()
}