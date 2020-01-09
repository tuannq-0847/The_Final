package com.karl.last_chat.view.profile.edit

import android.app.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class EditProfileViewModel(private val appRepository: AppRepository, application: Application) :
    BaseViewModel(appRepository, application) {

    val dataUser by lazy { SingleLiveEvent<User>() }
    val inforUser by lazy { SingleLiveEvent<Boolean>() }

    fun getInformationUser() {

        uiScope.launch {
            appRepository.getInforUsers()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        dataUser.value = data.getValue(User::class.java)
                    }
                })
        }
    }


    fun updateInforUser(bio: String, name: String, birth: Long, gender: String) {

        uiScope.launch {
            appRepository.updateInforBio(bio)
                .addOnFailureListener { error.value = it }
            appRepository.updateInforName(name)
                .addOnFailureListener { error.value = it }
            appRepository.updateInforGender(gender)
                .addOnFailureListener { error.value = it }
            appRepository.updateInforBirth(birth)
                .addOnFailureListener { error.value = it }
            inforUser.value = true
        }
    }
}