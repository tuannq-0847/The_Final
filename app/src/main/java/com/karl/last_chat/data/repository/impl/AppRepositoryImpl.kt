package com.karl.last_chat.data.repository.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.data.model.Rquest
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
    override suspend fun getFriends(): DatabaseReference =
        firebaseDatabase.getReference("${Constants.FRIEND}/${getCurrentUser()!!.uid}")

    override suspend fun insertFriend(uId: String): Task<Void> =
        firebaseDatabase.getReference("${Constants.FRIEND}/${getCurrentUser()!!.uid}")
            .child(uId).setValue(uId)

    private val generateName = "".generateName()

    private val storageFileRef = firebaseStorage.getReference("${Constants.MESSAGE}/$generateName")

    private val userId by lazy { firebaseAuth.currentUser?.uid }

    private val storageRef = firebaseStorage.getReference("${Constants.USER}/$userId/$generateName")


    override suspend fun uploadFileChat(
        uid: String,
        did: String,
        uri: Uri,
        previewName: String
    ): StorageTask<UploadTask.TaskSnapshot> =
        storageFileRef
            .putFile(uri).addOnSuccessListener {
                storageFileRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        sendMessage(
                            did, Message(
                                content = it.toString(),
                                idUserSend = getCurrentUser()!!.uid,
                                idUserRec = uid,
                                seen = getCurrentUser()!!.uid,
                                type = "file",
                                namePreview = previewName
                            )
                        )
                    }
                }
            }

    override suspend fun forgotPw(email: String) = firebaseAuth.sendPasswordResetEmail(email)

    override suspend fun uploadImage(
        uid: String,
        did: String,
        uri: Uri
    ): StorageTask<UploadTask.TaskSnapshot> =
        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        sendMessage(
                            did, Message(
                                content = it.toString(),
                                idUserSend = getCurrentUser()!!.uid,
                                idUserRec = uid,
                                seen = getCurrentUser()!!.uid,
                                type = "image"
                            )
                        )
                    }
                }
            }

    override suspend fun putByteAvatar(bytes: ByteArray): StorageTask<UploadTask.TaskSnapshot> =
        storageRef.putBytes(bytes)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        insertImagePath(it.toString())
                    }
                }
            }


    override suspend fun putByteBackground(bytes: ByteArray): StorageTask<UploadTask.TaskSnapshot> =
        storageRef.putBytes(bytes)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        insertImagePathBg(it.toString())
                    }
                }
            }

    override suspend fun removeFriendRequestFromSender(userId: String): Task<Void> {
        return firebaseDatabase.getReference(Constants.REQUEST)
            .child(userId)
            .child(getCurrentUser()!!.uid)
            .removeValue()
    }

    override suspend fun removeFriendRequest(userId: String): Task<Void> =
        firebaseDatabase.getReference(Constants.REQUEST)
            .child(getCurrentUser()!!.uid)
            .child(userId)
            .removeValue()

    override suspend fun checkFriendExist(userId: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.MESSAGE).child(getCurrentUser()!!.uid)

    override suspend fun getFriendRequest(): DatabaseReference =
        firebaseDatabase.getReference(Constants.REQUEST)
            .child(getCurrentUser()!!.uid)

    override suspend fun updateStatusSeen(
        idDiscuss: String,
        idChild: String,
        uid: String,
        seen: String
    ) {
        firebaseDatabase.getReference(Constants.MESSAGES).child(idDiscuss).child(idChild)
            .child("seen").setValue(if (seen.isEmpty()) uid else "both")
    }

    override suspend fun getChildStatusSeen(idDiscuss: String): Query =
        firebaseDatabase.getReference(Constants.MESSAGES)
            .child(idDiscuss)
            .orderByKey().limitToLast(1)

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

    override suspend fun setIdDiscuss(
        userId: String,
        disscussId: String
    ): Task<Void> {
        firebaseDatabase.getReference(Constants.MESSAGE).child(getCurrentUser()!!.uid)
            .child(userId)
            .setValue(disscussId)

        return firebaseDatabase.getReference(Constants.MESSAGE).child(userId)
            .child(getCurrentUser()!!.uid)
            .setValue(disscussId)
    }

    override suspend fun getDisscussMessages(idDiscuss: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.MESSAGES).child(idDiscuss)

    override suspend fun getIdDiscuss(userId: String, otherUid: String): DatabaseReference =
        firebaseDatabase.getReference(Constants.MESSAGE).child(userId).child(otherUid)

    override suspend fun sendMessage(idDiscuss: String, message: Message): Task<Void> =
        firebaseDatabase.getReference(Constants.MESSAGES)
            .child(idDiscuss)
            .child(generateIdMessage(idDiscuss))
            .setValue(message)

    override suspend fun sendFriendRequest(userId: String): Task<Void> {
        firebaseDatabase.getReference(Constants.REQUEST).child(userId)
            .child(getCurrentUser()!!.uid)
            .setValue(Rquest(getCurrentUser()!!.uid, 0))
        return firebaseDatabase.getReference(Constants.REQUEST).child(getCurrentUser()!!.uid)
            .child(userId)
            .setValue(Rquest(userId, 1))
    }

    override suspend fun checkIsSendRequest(userId: String): DatabaseReference {
        return firebaseDatabase.getReference(Constants.REQUEST).child(userId)
    }

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
        firebaseDatabase.getReference(Constants.USER)

    override suspend fun updateLocation(lat: Double, long: Double) {
        userId?.let {
            firebaseDatabase.getReference("${Constants.USER}/$it")
                .child("lat").setValue(lat)
            firebaseDatabase.getReference("${Constants.USER}/$it")
                .child("long").setValue(long)
        }
    }

    override suspend fun getMessages(): DatabaseReference =
        firebaseDatabase.getReference("${Constants.MESSAGE}/$userId")

    override suspend fun getInforUsers() =
        firebaseDatabase.getReference("${Constants.USER}/${getCurrentUser()!!.uid}")

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

    fun insertImagePath(url: String) {
        getCurrentUser()?.uid?.let {
            firebaseDatabase.getReference(Constants.USER)
                .child(it)
                .child("pathAvatar")
                .setValue(url)
        }
    }

    override suspend fun uploadBackground(uri: Uri) =
        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    runBlocking {
                        insertImagePathBg(it.toString())
                    }
                }
            }

    fun insertImagePathBg(url: String) =
        firebaseDatabase.getReference(Constants.USER)
            .child(getCurrentUser()!!.uid)
            .child("pathBackground")
            .setValue(url)

    override suspend fun updateUserStatus(online: Int) =
        firebaseDatabase.getReference(Constants.USER)
            .child(getCurrentUser()!!.uid)
            .child("online")
            .setValue(online)

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
