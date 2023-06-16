package com.c23ps323.bitesense.utils

import android.content.Context

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun saveUserCookie(cookie: String) {
        editor.putString(PREFS_COOKIE, cookie)
        editor.apply()
    }

    fun removeUserCookie() {
        editor.remove(PREFS_COOKIE)
        editor.apply()
    }

    fun getUserCookie(): String = preferences.getString(PREFS_COOKIE, "").toString()

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val PREFS_COOKIE = "user_cookie"
    }
}