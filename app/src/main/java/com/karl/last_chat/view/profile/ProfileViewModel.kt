package com.karl.last_chat.view.profile

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {
    val userData by lazy { SingleLiveEvent<User>() }
    val isFriendEvent by lazy { SingleLiveEvent<Boolean>() }
    val isSendRequest by lazy { SingleLiveEvent<Boolean>() }

    fun getUser(userId: String) {
        uiScope.launch {
            appRepository.getInforUser(userId).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    error.value = p0.toException()
                }

                override fun onDataChange(data: DataSnapshot) {
                    userData.value = data.getValue(User::class.java)
                }
            })
        }
    }

    fun checkIsFriend(userId: String) {
        uiScope.launch {
            appRepository.checkIsFriend(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        when {
                            data.key.isNullOrEmpty() -> {
                                isFriendEvent.value = false
                            }
                            else -> {
                                isFriendEvent.value = true
                            }
                        }
                    }
                })
        }
    }

    fun addFriend(userId: String) {
        uiScope.launch {
            appRepository.sendFriendRequest(userId)
                .addOnSuccessListener {
                    isSendRequest.value = true
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }
}
