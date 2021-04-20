package com.example.lassi.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SavedPreference {

    private const val EMAIL = "email"
    private const val USERNAME = "username"

    private  fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun  editor(context: Context, const:String, string: String){
        getSharedPreference(
            context
        )?.edit()?.putString(const,string)?.apply()
    }

    fun getEmail(context: Context)= getSharedPreference(
        context
    )?.getString(EMAIL,"")

    fun setEmail(context: Context, email: String){
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun setUsername(context: Context, username:String){
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME,"")

}