package com.karl.last_chat.utils.extensions

import android.view.View

fun View.OnClickListener.onClickViews(vararg view: View) {
    for (i in view.indices) {
        view[i].setOnClickListener(this)
    }
}
