package com.karl.last_chat.data.repository

import com.google.firebase.database.DatabaseReference
import com.karl.last_chat.data.remote.Message
import com.karl.last_chat.data.remote.ResultWrapper

interface AppRepository {

    suspend fun getMessagesFrom(userId: String): ResultWrapper<DatabaseReference>
}
