package com.karl.last_chat.view.personal

import android.net.Uri
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.DialogEnum
import com.karl.last_chat.utils.PermissionStateEnum
import com.karl.last_chat.utils.SingleLiveEvent

class SharedViewModel : BaseViewModel(null) {

    val eventAvatar by lazy { SingleLiveEvent<DialogEnum>() }
    val uriImage by lazy { SingleLiveEvent<Uri>() }
    val eventRequestPermission by lazy { SingleLiveEvent<PermissionStateEnum>() }
}
