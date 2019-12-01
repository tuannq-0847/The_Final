package com.karl.last_chat.utils

import java.text.DecimalFormat

val a: String = "64dp\n" +
        "16dp\n" +
        "24sp\n" +
        "18sp\n" +
        "14sp\n" +
        "1px\n" +
        "52dp\n";

fun main() {
    val rate = 1.8f
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