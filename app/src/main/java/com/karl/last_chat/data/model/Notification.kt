package com.karl.last_chat.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notification(
    val contents: String = "",
    val from: String = ""
) : Parcelable
