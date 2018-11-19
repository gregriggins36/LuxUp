package com.frate.luxup.repository

import android.content.SharedPreferences

class SharedPreferencesRepository(private val sharedPreferences: SharedPreferences) {
    fun userId(): String = sharedPreferences.getString(USER_ID, "")!!
    fun userEmail(): String = sharedPreferences.getString(USER_EMAIL, "")!!
    fun userName(): String = sharedPreferences.getString(USER_NAME, "")!!
    fun userPhoto(): String = sharedPreferences.getString(USER_PHOTO, "")!!
    fun userPhoneNumber(): String = sharedPreferences.getString(USER_PHONE_NUMBER, "")!!

    fun setUserId(userId: String) = sharedPreferences.edit()
        .putString(USER_ID, userId)
        .apply()

    fun setUserEmail(email: String) = sharedPreferences.edit()
        .putString(USER_EMAIL, email)
        .apply()

    fun setUserName(displayName: String) = sharedPreferences.edit()
        .putString(USER_NAME, displayName)
        .apply()

    fun setUserPhoto(userPhoto: String) = sharedPreferences.edit()
        .putString(USER_PHOTO, userPhoto)
        .apply()

    fun setPhoneNumber(phoneNumber: String) = sharedPreferences.edit()
        .putString(USER_PHONE_NUMBER, phoneNumber)
        .apply()

    private fun remove(key: String) = sharedPreferences.edit().remove(key).apply()

    fun removeUserId() = remove(USER_ID)
    fun removeUserEmail() = remove(USER_EMAIL)
    fun removeUserName() = remove(USER_EMAIL)
    fun removeUserPhoto() = remove(USER_PHOTO)
    fun removeUserPhoneNumber() = remove(USER_PHONE_NUMBER)

    private companion object {
        const val USER_ID = "user_id"
        const val USER_EMAIL = "user_email"
        const val USER_NAME = "user_name"
        const val USER_PHOTO = "user_photo"
        const val USER_PHONE_NUMBER = "user_phone_number"
    }
}