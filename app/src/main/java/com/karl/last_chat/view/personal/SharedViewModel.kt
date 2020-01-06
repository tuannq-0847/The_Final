package com.karl.last_chat.view.personal

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.SingleLiveEvent

class SharedViewModel(private val app: Application) : BaseViewModel(null, app) {

    val eventAvatar by lazy { SingleLiveEvent<DialogEnum>() }
    val uriImage by lazy { SingleLiveEvent<Uri>() }
    val bitmapImage by lazy { SingleLiveEvent<Bitmap>() }
    val imageChat by lazy { SingleLiveEvent<Uri>() }
    val eventLocationName by lazy { SingleLiveEvent<String>() }
}
