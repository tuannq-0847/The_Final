package com.karl.last_chat.view.personal

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.runBlocking

class PersonalViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {

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

    fun getInforUser() {
        runBlocking {
            showLoading()
            appRepository.getInforUsers().addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    error.value = databaseError.toException()
                }

                override fun onDataChange(data: DataSnapshot) {
                    dataPersonal.value = data.getValue(User::class.java)
                }

            })
        }
    }
}
