package com.karl.last_chat.data.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.karl.last_chat.data.remote.CallBack
import com.karl.last_chat.data.remote.Message
import com.karl.last_chat.data.remote.ResultWrapper
import com.karl.last_chat.data.remote.safeApiCall
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred

class AppRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val dispatcher: CoroutineDispatcher
): AppRepository {
    override suspend fun getMessagesFrom(userId: String): ResultWrapper<DatabaseReference> {
        return safeApiCall(dispatcher){
            firebaseDatabase.reference.child("${Constants.MESSAGE}/")
        }
    }
}
