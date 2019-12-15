package com.karl.last_chat.data.remote

data class Message(
    var userId: Int,
    var messageId: Int,
    var content: String,
    var isSend: Boolean
)
