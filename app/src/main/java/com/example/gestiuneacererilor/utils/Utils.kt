package com.example.gestiuneacererilor.utils

import android.content.Context
import android.text.Editable
import java.text.SimpleDateFormat
import java.util.*

fun Any.generateLogTag(): String = this::class.java.simpleName

fun Any.convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return format.format(date)
}

fun getCurrentUserEmail(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_FIREBASE_USER_EMAIL)
        .toString()
}

fun getCurrentUserId(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_ID).toString()
}


fun getCurrentUserDisplayName(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_FIREBASE_USER_NAME)
        .toString()
}

fun getCurrentUserNume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_NUME)
        .toString()
}

fun getCurrentUserPrenume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_PRENUME)
        .toString()
}

fun getCurrentUserFacultate(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_FACULTATE).toString()
}

fun getCurrentStudentAn(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_AN).toString()
}

fun getCurrentStudentCiclu(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_CICLU).toString()
}

fun determineCurrentTypeUser(email: String): Constants.UserType {
    return when {
        email.contains("@stud.ase.ro") -> Constants.UserType.STUDENT
        else -> Constants.UserType.PROFESSOR
    }
}
