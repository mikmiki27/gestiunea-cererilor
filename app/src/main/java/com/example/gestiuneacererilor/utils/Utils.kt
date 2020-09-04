package com.example.gestiuneacererilor.utils

import android.content.Context
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
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_FIREBASE_DISPLAY_NAME)
        .toString()
}

fun getCurrentLicentaAcceptati(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_LICENTA_ACCEPTATI)
        .toString()
}

fun getCurrentDisertatieAcceptati(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_DISERTATIE_ACCEPTATI)
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

fun getProfesorLicentaEchipa(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_ECHIPA_LICENTA)
        .toString()
}

fun getProfesorMasterEchipa(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_ECHIPA_MASTER)
        .toString()
}

fun getCurrentUserFacultate(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_FACULTATE).toString()
}

fun getCurrentStudentAn(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_AN).toString()
}

fun getCurrentStudentProfesorCoordonator(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_PROFESOR_COORDONATOR)
        .toString()
}

fun getCurrentStudentCiclu(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.CURRENT_USER_CICLU).toString()
}

fun getCurrentStudentProfesorCoordonatorId(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_COORDONATOR_ID).toString()
}

fun getCurrentStudentProfesorCoordonatorDisplayName(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_COORDONATOR_DISPLAY_NAME)
        .toString()
}

fun determineCurrentTypeUser(email: String): Constants.UserType {
    return when {
        email.contains("@stud.ase.ro") -> Constants.UserType.STUDENT
        else -> Constants.UserType.PROFESSOR
    }
}

fun createDate(date: String, oraI: String): Date {
    val data = "$date $oraI"

    return SimpleDateFormat(
        "dd-MM-yyyy HH:mm",
        Locale.getDefault()
    ).parse(data)
}

fun isCurrentDateBiggerThatDateToCompare(dateToCompare: Date): Boolean {
    val cal = Calendar.getInstance()

    val stringData =
        """${cal.time.day}-${cal.time.month}-${cal.time.year} ${cal.time.hours}:${cal.time.minutes}"""
    val currentDate = SimpleDateFormat(
        "dd-MM-yyyy HH:mm",
        Locale.getDefault()
    ).parse(stringData)

    return currentDate > dateToCompare
}
