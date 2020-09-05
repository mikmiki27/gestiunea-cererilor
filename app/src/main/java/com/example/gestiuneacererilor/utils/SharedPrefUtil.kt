package com.example.gestiuneacererilor.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil {

    companion object {
        private const val PREFS_NAME = "CERERI_PREF"

        const val STUDENT_CURRENT_ID = "student_current_id"
        const val STUDENT_CURRENT_NUME = "student_current_nume"
        const val STUDENT_CURRENT_PRENUME = "student_current_prenume"
        const val STUDENT_FULL_NAME = "student_full_name"
        const val STUDENT_CURRENT_EMAIL = "student_current_email"
        const val STUDENT_CURRENT_FACULTATE = "student_current_facultate"
        const val STUDENT_CICLU = "student_ciclu"
        const val STUDENT_AN = "student_an"
        const val STUDENT_PROFESOR_COORDONATOR_EMAIL = "student_profesor_coordonator_email"
        const val STUDENT_PROFESOR_COORDONATOR_FULL_NAME = "student_profesor_coordonator_full_name"
        const val STUDENT_PROFESOR_COORDONATOR_ID = "student_profesor_coordonator_id"
        const val STUDENT_TITLU_LUCRARE = "student_titlu_lucrare"

        const val PROFESOR_CURRENT_ID = "profesor_current_id"
        const val PROFESOR_CURRENT_NUME = "profesor_current_nume"
        const val PROFESOR_CURRENT_PRENUME = "profesor_current_prenume"
        const val PROFESOR_FULL_NAME = "profesor_full_name"
        const val PROFESOR_CURRENT_EMAIL = "profesor_current_email"
        const val PROFESOR_CERINTE_LICENTA = "profesor_cerinte_licenta"
        const val PROFESOR_CERINTE_MASTER = "profesor_cerinte_master"
        const val PROFESOR_CURRENT_FACULTATE = "profesor_current_facultate"
        const val PROFESOR_ECHIPA_LICENTA = "profesor_echipa_licenta"
        const val PROFESOR_ECHIPA_MASTER = "profesor_echipa_master"
        const val PROFESOR_LICENTA_ACCEPTATI = "profesor_licenta_acceptati"
        const val PROFESOR_DISERTATIE_ACCEPTATI = "profesor_disertatie_acceptati"

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