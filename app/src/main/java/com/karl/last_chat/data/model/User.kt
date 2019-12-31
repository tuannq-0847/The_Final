package com.karl.last_chat.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String = "",
    var userName: String = "Name",
    var birthday: String? = null,
    var gender: String? = null,
    var bio: String = "",
    var pathAvatar: String = "",
    var pathBackground: String = "",
    var online: Int = 0,
    var long: Double = 0.0,
    var lat: Double = 0.0,
    var deviceToken: String = ""
) : Parcelable
