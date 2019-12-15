package com.karl.last_chat.view.personal

import android.net.Uri
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.SingleLiveEvent

class SharedViewModel : BaseViewModel() {

    val eventAvatar by lazy { SingleLiveEvent<DialogEnum>() }
    val uriImage by lazy { SingleLiveEvent<Uri>() }
}
