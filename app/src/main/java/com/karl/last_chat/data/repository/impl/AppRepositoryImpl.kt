package com.karl.last_chat.data.repository.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.storage.FirebaseStorage
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
