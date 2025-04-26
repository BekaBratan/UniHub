package com.example.unihub.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

class SharedProvider(private val context: Context) {
    private val sharedToken = "AccessToken"
    private val email = "Email"
    private val isAuthorized = "isAuthorized"

    private val preferences: SharedPreferences
        get() = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    private val systemPreferences: SharedPreferences
        get() = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

//    fun saveUser(user: AuthorizationResponse) {
//        val editor = preferences.edit()
//        editor.clear()
//        editor.putBoolean("isAuthorized", true)
//        editor.apply()
//        saveToken(user.access_token.toString())
//    }

    fun isAuthorized(): Boolean {
        return preferences.getBoolean(isAuthorized, false)
    }

    fun saveEmail(_email: String) {
        preferences.edit() { putString(email, _email) }
    }

    fun saveToken(token: String) {
        preferences.edit() { putString(sharedToken, token) }
    }

    fun getEmail(): String {
        return preferences.getString(email, "without_email").toString()
    }

    fun getToken():String {
        return "Bearer ${preferences.getString(sharedToken, "without_token")}"
    }

    fun setToken(token: String) {
        preferences.edit() {
            putString(sharedToken, token)
            Log.d("AAA", "setToken: $token")
        }
    }

    fun clearShared() {
        preferences.edit() { clear() }
    }

}