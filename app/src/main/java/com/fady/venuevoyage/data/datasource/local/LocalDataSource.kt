package com.fady.venuevoyage.data.datasource.local

import android.content.SharedPreferences
import com.fady.venuevoyage.data.models.UserProfile
import com.google.gson.Gson

object LocalDataSource {

    const val FILE_PREFERENCES = "venuevoyage_prefernce"
    val gson = Gson()
    private var preferences: SharedPreferences? = null

    fun initialize(preferences: SharedPreferences?) {
        LocalDataSource.preferences = preferences
    }

    var userProfile: UserProfile?
        get() = preferences.processStoredObject(SharedPreferencesConstants.CURRENT_USER.value)
        set(value) = preferences.storeObject(SharedPreferencesConstants.CURRENT_USER.value, value)

    var token: String?
        get() = preferences.processStoredObject(SharedPreferencesConstants.TOKEN.value)
        set(value) = preferences.storeObject(SharedPreferencesConstants.TOKEN.value, value)

    fun isUserLoggedIn(): Boolean {
        return token != null
    }

    fun isCorrectEmailAndPassword(email: String, password: String): Boolean {
        userProfile.let {
            return it?.email == email && it.password == password
        }
    }

    fun clearToken() {
        preferences?.edit()?.remove(SharedPreferencesConstants.TOKEN.value)?.apply()
    }

}

