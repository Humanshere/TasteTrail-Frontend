package com.example.tastetrail.data

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private lateinit var sharedPreferences: SharedPreferences
    private const val PREF_NAME = "tastetrail_prefs"
    private const val AUTH_TOKEN_KEY = "auth_token"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var authToken: String?
        get() = sharedPreferences.getString(AUTH_TOKEN_KEY, null)
        set(value) {
            if (value == null) {
                sharedPreferences.edit().remove(AUTH_TOKEN_KEY).apply()
            } else {
                sharedPreferences.edit().putString(AUTH_TOKEN_KEY, value).apply()
            }
        }
}