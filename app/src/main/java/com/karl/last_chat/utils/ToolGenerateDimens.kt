package com.karl.last_chat.utils

import java.text.DecimalFormat

val a: String = "64dp\n" +
        "16dp\n" +
        "24sp\n" +
        "18sp\n" +
        "14sp\n" +
        "1px\n" +
        "52dp\n" +
        "24dp\n" +
        "32dp\n" +
        "512dp\n" +
        "144dp\n" +
        "8dp\n" +
        "352dp\n" +
        "74dp\n" +
        "244dp\n" +
        "36dp\n" +
        "120dp\n" +
        "4dp\n" +
        "12dp\n" +
        "48dp\n" +
        "20sp\n" +
        "56dp\n" +
        "2dp\n" +
        "1dp\n" +
        "10dp\n"

fun main() {
    val rate = 0.8f
    var temp = ""
    var b: Float
    for (i in a.indices) {
        temp += a[i]
        if (a[i] == '\n') {
            if (temp.substring(temp.length - 3, temp.length - 1) == "px") {
                print(temp)
                temp = ""
            } else {
                b = temp.substring(0, temp.length - 3).toInt() * rate
                println(
                    DecimalFormat("###.#").format(b) + temp.substring(
                        temp.length - 3,
                        temp.length - 1
                    )
                )
                temp = ""
            }
        }
    }
}