package com.karl.last_chat.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.data.model.User

interface AppRepository {

    suspend fun getMessagesFrom(userId: String): DatabaseReference

    suspend fun getInstanceUserUid(): Task<InstanceIdResult>

    suspend fun createUser(email: String, password: String): Task<AuthResult>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun insertUser(user: User): Task<Void>

    suspend fun isLoggedIn(): Boolean

    suspend fun uploadCover(uri: Uri): UploadTask

    suspend fun uploadAvatar(uri: Uri): StorageTask<UploadTask.TaskSnapshot>

    suspend fun updateUserStatus(online: Int): Task<Void>

    suspend fun getInforUsers(): DatabaseReference

    suspend fun getMessages(): DatabaseReference

    suspend fun updateLocation(lat: Double, long: Double)

    suspend fun getUsers(): DatabaseReference

    fun getCurrentUser(): FirebaseUser?

    suspend fun getInforUser(userId: String): DatabaseReference

    suspend fun getInstanceIdUser(): Task<InstanceIdResult>

    suspend fun updateInstanceId(instanceId: String): Task<Void>

    suspend fun checkIsSendRequest(userId: String): DatabaseReference

    suspend fun sendFriendRequest(userId: String): Task<Void>

    suspend fun sendMessage(idDiscuss: String, message: Message): Task<Void>

    suspend fun getIdDiscuss(userId: String, otherUid: String): DatabaseReference

    suspend fun setIdDiscuss(userId: String, disscussId: String): Task<Void>

    suspend fun getDisscussMessages(idDiscuss: String): DatabaseReference

    fun generateIdMessage(idDiscuss: String): String

    suspend fun saveNotification(receiveId: String, notification: Notification): Task<Void>

    suspend fun generateNotificationId(receiveId: String, notification: Notification): String

//    fun generateIdDiscuss(userId: String, otherUid: String): String

    suspend fun logout()

    suspend fun getChildStatusSeen(idDiscuss: String): Query

    suspend fun updateStatusSeen(idDiscuss: String, idChild: String, uid: String, seen: String)

    suspend fun uploadBackground(uri: Uri): StorageTask<UploadTask.TaskSnapshot>

    suspend fun getFriendRequest(): DatabaseReference

    suspend fun checkFriendExist(userId: String): DatabaseReference

    suspend fun removeFriendRequest(userId: String): Task<Void>

    suspend fun removeFriendRequestFromSender(userId: String): Task<Void>

    suspend fun putByteAvatar(bytes: ByteArray): StorageTask<UploadTask.TaskSnapshot>


    suspend fun putByteBackground(bytes: ByteArray): StorageTask<UploadTask.TaskSnapshot>

    suspend fun uploadImage(
        uid: String,
        did: String,
        uri: Uri
    ): StorageTask<UploadTask.TaskSnapshot>
}
