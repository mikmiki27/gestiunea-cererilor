package com.example.gestiuneacererilor.utils

import java.text.SimpleDateFormat
import java.util.*

fun Any.generateLogTag(): String = this::class.java.simpleName

fun Any.convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return format.format(date)
}
