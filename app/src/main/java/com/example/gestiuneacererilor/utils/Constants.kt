package com.example.gestiuneacererilor.utils

object Constants {
    val APP_TAG = "gestiunea_cererilor"

    // Shared Preferences
    const val SHARED_PREF_FILE = "sfaredPrefFile"

    const val USER_KEY = "userKey"

    const val GENERAL_TAG = "general"

    //Profile
    const val FROM_PROFILE = "fromProfile"
    const val FROM_PROFILE_TAGS_REQUEST_CODE = 101

    //Notifications
    const val NOTIFICATION_CHANNEL_ID = "default-notification-channel"

    enum class UserType {
        STUDENT, PROFESSOR
    }

    enum class StatusCerere {
        PROGRES, ACCEPTATA, RESPINSA, ANULATA
    }

    enum class TipCerere {
        LICENTA, DISERTATIE
    }
}