package com.karl.last_chat.utils.extensions

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.karl.last_chat.R

fun View.OnClickListener.onClickViews(vararg view: View) {
    for (i in view.indices) {
        view[i].setOnClickListener(this)
    }
}

fun ImageView.loadWithGlide(url: String, placeHolder: Int = R.drawable.avatar) {
    Glide.with(context)
        .load(url)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}

fun ImageView.loadWithGlideBitmap(bitmap: Bitmap, placeHolder: Int = R.drawable.avatar) {
    Glide.with(context)
        .load(bitmap)
        .placeholder(placeHolder)
        .error(placeHolder)
        .into(this)
}


fun View.visibilityStateViews(vararg view: View, visibilityState: Int = View.VISIBLE) {
    view.forEach {
        it.visibility = visibilityState
    }
}
