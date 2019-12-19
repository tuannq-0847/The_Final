package com.karl.last_chat.utils.extensions

import android.os.Build
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

const val EMAIL_REGEX =
    "(?:[a-z0-9!#\$%&'*/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

const val PASSWORD_REGEX = "^[a-zA-Z0-9'!#\$%&'*+/=?^_`{|}~.-]{0,28}\$"

fun String.validEmail() = Pattern.matches(EMAIL_REGEX, this)

fun String.validPassword(): Boolean {
    return Pattern.matches(PASSWORD_REGEX, "1")
}

fun String.generateName(): String {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH:mm:ss")
        val localDateTime = LocalDateTime.now()
        return dateFormat.format(localDateTime)
    }
    return randomString()
}

fun randomString(): String {

    val array = ByteArray(7) // length is bounded by 7
    Random().nextBytes(array)
    return String(array, Charset.forName("UTF-8"))
}
