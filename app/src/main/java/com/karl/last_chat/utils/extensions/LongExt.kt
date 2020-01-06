package com.karl.last_chat.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTime() = Calendar.getInstance()

fun Long.getCurrentAge(): Int {
    val currenYear = getCurrentTime().get(Calendar.YEAR)
    val date = Date(this)
    val format = SimpleDateFormat("yyyy")
    val realAge = currenYear - format.format(date).toInt()
    return realAge
}
