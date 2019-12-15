package com.karl.last_chat.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.iid.InstanceIdResult
import com.karl.last_chat.data.model.User

interface AppRepository {

    suspend fun getMessagesFrom(userId: String): DatabaseReference

    suspend fun getInstanceUserUid(): Task<InstanceIdResult>

    suspend fun createUser(email: String, password: String): Task<AuthResult>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun insertUser(user:User): Task<Void>

    suspend fun isLoggedIn(): Boolean
}
