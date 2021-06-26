package com.kasiopec.qrcovid

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsManager(private val context: Context){

    companion object {
        const val PREF_USER_PREFERENCES = "USER_PREFS"
        const val KEY_USER_NAME = "user_name"
    }

    private fun getSharedPref(): SharedPreferences {
        return context.getSharedPreferences(PREF_USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setUserName(name : String){
        getSharedPref().edit{
            putString(KEY_USER_NAME, name)
        }
    }

    fun getUserName() : String {
        return getSharedPref().getString(KEY_USER_NAME, null) ?: "User"
    }

}


