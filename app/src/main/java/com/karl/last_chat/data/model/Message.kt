package com.karl.last_chat.data.model

data class Message(
    var idMessage: String = "",
    var content: String = "",
    var idUserRec: String = "",
    var idUserSend: String = ""
)
