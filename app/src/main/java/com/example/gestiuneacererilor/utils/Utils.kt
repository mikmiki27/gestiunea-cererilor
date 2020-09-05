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

//-------------------------STUDENT----------------------------

fun getStudentCurrentID(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CURRENT_ID).toString()
}

fun getStudentCurrentNume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CURRENT_NUME).toString()
}

fun getStudentCurrentPrenume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CURRENT_PRENUME).toString()
}

fun getStudentCurrentFullName(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_FULL_NAME).toString()
}

fun getStudentCurrentEmail(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CURRENT_EMAIL).toString()
}

fun getStudentCurrentFacultate(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CURRENT_FACULTATE).toString()
}

fun getStudentCiclu(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_CICLU).toString()
}

fun getStudentAn(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_AN).toString()
}

fun getStudentProfesorCoordonatorEmail(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_EMAIL).toString()
}

fun getStudentProfesorCoordonatorFullName(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_FULL_NAME).toString()
}

fun getStudentProfesorCoordonatorID(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_PROFESOR_COORDONATOR_ID).toString()
}

fun getStudentTitluLucrare(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.STUDENT_TITLU_LUCRARE).toString()
}

//-------------------------PROFESOR---------------------------

fun getProfesorCurrentID(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CURRENT_ID).toString()
}

fun getProfesorCurrentNume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CURRENT_NUME).toString()
}

fun getProfesorCurrentPrenume(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CURRENT_PRENUME).toString()
}

fun getProfesorCurrentFullName(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_FULL_NAME).toString()
}

fun getProfesorCurrentEmail(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CURRENT_EMAIL).toString()
}

fun getProfesorCerinteLicenta(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CERINTE_LICENTA).toString()
}

fun getProfesorCerinteMaster(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CERINTE_MASTER).toString()
}

fun getProfesorCurrentFacultate(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_CURRENT_FACULTATE).toString()
}

fun getProfesorLicentaEchipa(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_ECHIPA_LICENTA).toString()
}

fun getProfesorMasterEchipa(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_ECHIPA_MASTER).toString()
}

fun getCurrentLicentaAcceptati(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_LICENTA_ACCEPTATI).toString()
}

fun getCurrentDisertatieAcceptati(context: Context): String {
    return SharedPrefUtil.getStringValue(context, SharedPrefUtil.PROFESOR_DISERTATIE_ACCEPTATI).toString()
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
/*
    val stringData =
        """${cal.time.day}-${cal.time.month}-${cal.time.year} ${cal.time.hours}:${cal.time.minutes}"""
    val currentDate = SimpleDateFormat(
        "dd-MM-yyyy HH:mm",
        Locale.getDefault()
    ).parse(stringData)*/

    return cal.time > dateToCompare
}
