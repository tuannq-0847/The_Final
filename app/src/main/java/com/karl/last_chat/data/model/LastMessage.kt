package com.karl.last_chat.data.model

data class LastMessage(
    var idLast: String = "",
    var lastContent: String,
    var onlineStatus: Int = 1,
    var pathImage: String = "",
    var nameSender: String,
    var seen: Int = 0,
    var idUserSend: String = "",
    var idUserRec: String = ""
)
