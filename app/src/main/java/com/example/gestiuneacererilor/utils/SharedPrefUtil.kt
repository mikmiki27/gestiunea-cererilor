package com.example.gestiuneacererilor.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil {

    companion object {
        var PREFS_NAME = "CERERI_PREF"
        val CURRENT_FIREBASE_USER_EMAIL = "current_firebase_user_email"
        val CURRENT_FIREBASE_USER_NAME = "current_firebase_user_name"

        fun addKeyValue(context: Context, key: String, value: String) {
            val editor = sharedPrefs(context).edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun getStringValue(context: Context?, key: String): String? {
            return if (context != null) {
                sharedPrefs(context).getString(key, "")
            } else ""
        }

        private fun sharedPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }
}