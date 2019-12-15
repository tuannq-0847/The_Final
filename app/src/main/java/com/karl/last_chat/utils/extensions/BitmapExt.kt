package com.karl.last_chat.utils.extensions

import android.graphics.Bitmap
import android.graphics.Matrix


fun Bitmap.rotate(bitmap: Bitmap, orientation:Int): Bitmap {
    if(orientation<=0){
        return this
    }
    val matrix = Matrix()
    matrix.postRotate(orientation.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
