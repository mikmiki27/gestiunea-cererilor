package com.example.gestiuneacererilor.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil {

    companion object {
        private const val PREFS_NAME = "CERERI_PREF"
        const val CURRENT_FIREBASE_USER_EMAIL = "current_firebase_user_email"
        const val CURRENT_FIREBASE_DISPLAY_NAME = "current_firebase_user_name"
        const val CURRENT_USER_ID = "current_user_id"
        const val CURRENT_USER_NUME = "current_user_nume"
        const val CURRENT_USER_PRENUME = "current_user_prenume"
        const val CURRENT_USER_FACULTATE = "current_user_facultate"
        const val CURRENT_USER_AN = "current_user_an"
        const val CURRENT_USER_CICLU = "current_user_ciclu"
        const val CURRENT_USER_PROFESOR_COORDONATOR = "current_user_profesor_coordonator"
        const val CURRENT_USER_TITLU_LUCRARE = "current_user_titlu_lucrare"
        const val CURRENT_USER_ECHIPA_LICENTA = "current_user_echipa_licenta"
        const val CURRENT_USER_ECHIPA_MASTER = "current_user_echipa_master"
        const val CURRENT_USER_CERINTE_LICENTA = "current_user_cerinte_licenta"
        const val CURRENT_USER_CERINTE_MASTER = "current_user_cerinte_master"
        const val CURRENT_USER_LICENTA_ACCEPTATI = "current_user_licenta_acceptati"
        const val CURRENT_USER_DISERTATIE_ACCEPTATI = "current_user_disertatie_accpetati"
        const val PROFESOR_COORDONATOR_ID = "profesor_coordonator_id"
        const val PROFESOR_COORDONATOR_DISPLAY_NAME = "profesor_coordonator_display_name"

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

        fun removeStringValue(context: Context, key: String){
            val editor = sharedPrefs(context).edit()
            editor.remove(key)
            editor.apply()
        }


        private fun sharedPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

}