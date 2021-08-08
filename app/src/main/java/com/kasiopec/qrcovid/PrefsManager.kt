package com.kasiopec.qrcovid

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsManager @Inject constructor(private val preferences : SharedPreferences){

    companion object {
        const val KEY_COVID_PASS_CODE = "user_covid_pass_code"
        const val KEY_USER_NAME = "user_name"
    }

    fun setUserName(name : String){
        preferences.edit{
            putString(KEY_USER_NAME, name)
        }
    }

    fun removeCovidPassCode(){
        preferences.edit{
            remove(KEY_COVID_PASS_CODE)
        }
    }

    fun getUserName() : String {
        return preferences.getString(KEY_USER_NAME, null) ?: "User"
    }

    fun setCovidPassCode(code : String){
        preferences.edit{
            putString(KEY_COVID_PASS_CODE, code)
        }
    }

    fun getCovidPassCode() : String? {
        return preferences.getString(KEY_COVID_PASS_CODE, null)
    }

}


