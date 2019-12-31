package com.karl.last_chat.data.repository.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.storage.FirebaseStorage
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.Constants
import com.karl.last_chat.utils.extensions.generateName
import kotlinx.coroutines.runBlocking

class AppRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseInstanceId: FirebaseInstanceId
) : AppRepository {
    override suspend fun logout() = firebaseAuth.signOut()

    override suspend fun generateNotificationId(
        receiveId: String,
        notification: Notification
    ): String =
        firebaseDatabase.getReference(Constants.NOTIFICATION)
            .child(receiveId).push().key!!

    override suspend fun saveNotification(receiveId: String, notification: Notification) =
        firebaseDatabase.getReference(Constants.NOTIFICATION).child(receiveId).child(
            generateNotificationId(receiveId, notification)
        ).setValue(notification)

    override fun generateIdMessage(idDiscuss: String): String =
        firebaseDatabase.reference.child(Constants.MESSAGES)
            .child(idDiscuss).push().key!!

    override suspend fun setIdDiscuss(userId: String, disscussId: String): Task<Void> {
        firebaseDatabase.getReference(Constants.MESSAGE).child(getCurrentUser()!!.uid)
            .setValue(disscussId)

        return firebaseDatabase.getReference(Constants.MESSAGE).child(userId)
            .setValue(disscussId)
    }

    override suspend fun getDisscussMessages(idDiscuss: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.MESSAGES).child(idDiscuss)

    override suspend fun getIdDiscuss(userId: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.MESSAGE).child(userId)

    override suspend fun sendMessage(idDiscuss: String, message: Message): Task<Void> =
        firebaseDatabase.getReference(Constants.MESSAGES)
            .child(idDiscuss)
            .child(generateIdMessage(idDiscuss))
            .setValue(message)

    override suspend fun sendFriendRequest(userId: String): Task<Void> =
        firebaseDatabase.getReference(Constants.FRIEND).child(getCurrentUser()!!.uid)
            .setValue(userId)

    override suspend fun checkIsFriend(userId: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.FRIEND).child(userId)

    override suspend fun updateInstanceId(instanceId: String) =
        firebaseDatabase.getReference("${Constants.USER}/${getCurrentUser()!!.uid}")
            .child("deviceToken")
            .setValue(instanceId)

    override suspend fun getInstanceIdUser(): Task<InstanceIdResult> = firebaseInstanceId.instanceId

    override suspend fun getInforUser(userId: String): DatabaseReference = firebaseDatabase
        .getReference("${Constants.USER}/$userId")

    override fun getCurrentUser(): FirebaseUser? =
        firebaseAuth.currentUser

    override suspend fun getUsers(): DatabaseReference =
        firebaseDatabase.getReference("${Constants.USER}")

    override suspend fun updateLocation(lat: Double, long: Double) {
        firebaseDatabase.getReference("${Constants.USER}/$userId")
            .child("lat").setValue(lat)
        firebaseDatabase.getReference("${Constants.USER}/$userId")
            .child("long").setValue(long)
    }

    override suspend fun getMessages(): DatabaseReference =
        firebaseDatabase.getReference("${Constants.MESSAGES}/$userId")

    override suspend fun getInforUsers() =
        firebaseDatabase.getReference("${Constants.USER}/$userId")

    private val userId = firebaseAuth.currentUser?.uid

    private val generateName = "".generateName()

    private val storageRef = firebaseStorage.getReference("${Constants.USER}/$userId/$generateName")

    override suspend fun uploadCover(uri: Uri) =
        firebaseStorage.getReference("${Constants.USER}/$userId/$generateName")
            .putFile(uri)

    override suspend fun uploadAvatar(uri: Uri) =
        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        insertImagePath(it.toString())
                    }
                }
            }

    fun insertImagePath(url: String) =
        firebaseDatabase.getReference(Constants.USER)
            .child(userId!!)
            .child("pathAvatar")
            .setValue(url)

    override suspend fun updateUserStatus(online: Int) {

    }

    override suspend fun isLoggedIn(): Boolean {
        return !firebaseAuth.uid.isNullOrEmpty()
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    override suspend fun insertUser(user: User): Task<Void> =
        firebaseDatabase.reference.child("${Constants.USER}/")
            .child(user.uid).setValue(user)

    override suspend fun createUser(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun getInstanceUserUid(): Task<InstanceIdResult> {
        return firebaseInstanceId.instanceId
    }

    override suspend fun getMessagesFrom(userId: String): DatabaseReference {
        return firebaseDatabase.reference.child("${Constants.MESSAGE}/")
    }
}
