package com.karl.last_chat.view.personal

import android.app.Application
import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.runBlocking

class PersonalViewModel(private val appRepository: AppRepository, application: Application) :
    BaseViewModel(appRepository, application) {

    val eventUploadAvatar by lazy { SingleLiveEvent<Boolean>() }

    val dataPersonal by lazy { SingleLiveEvent<User>() }
    fun uploadCover(uri: Uri) {
        runBlocking {
            showLoading()
            appRepository.uploadAvatar(uri)
                .addOnSuccessListener {
                    eventUploadAvatar.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun uploadAvatar(uri: Uri) {
        runBlocking {
            showLoading()
            appRepository.uploadAvatar(uri)
                .addOnSuccessListener {
                    eventUploadAvatar.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun putByteAvatar(byteArray: ByteArray) {
        runBlocking {
            showLoading()
            appRepository.putByteAvatar(byteArray)
                .addOnSuccessListener {
                    eventUploadAvatar.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun putByteBackground(byteArray: ByteArray) {
        runBlocking {
            showLoading()
            appRepository.putByteBackground(byteArray)
                .addOnSuccessListener {
                    eventUploadAvatar.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }


    fun uploadBackground(uri: Uri) {
        runBlocking {
            showLoading()
            appRepository.uploadBackground(uri)
                .addOnSuccessListener {
                    eventUploadAvatar.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun getInforUser() {
        runBlocking {
            showLoading()
            appRepository.getInforUsers().addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    error.value = databaseError.toException()
                }

                override fun onDataChange(data: DataSnapshot) {
                    hideLoading()
                    dataPersonal.value = data.getValue(User::class.java)
                }

            })
        }
    }

    fun logout() {
        runBlocking {
            appRepository.logout()
        }
    }
}
