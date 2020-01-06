package com.karl.last_chat.view.profile

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.Rquest
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.FriendRequestEnum
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel(private val appRepository: AppRepository, application: Application) :
    BaseViewModel(appRepository, application) {
    val userData by lazy { SingleLiveEvent<User>() }
    val isFriendEvent by lazy { SingleLiveEvent<FriendRequestEnum>() }

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
            appRepository.checkFriendExist(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        when {
                            !data.exists() -> {
                                checkIsSendFriendRequest(userId)
                            }
                            else -> {
                                data.children.forEach {
                                    if (it.getValue(String::class.java) == userId) {
                                        isFriendEvent.value = FriendRequestEnum.ACCEPTED
                                    } else isFriendEvent.value = FriendRequestEnum.REJECTED
                                }
                            }
                        }
                    }
                })
        }
    }

    fun checkIsSendFriendRequest(userId: String) {
        uiScope.launch {
            appRepository.checkIsSendRequest(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        Log.d("dataS", data.value.toString())
                        when {
                            !data.exists() -> {
                                isFriendEvent.value = FriendRequestEnum.REJECTED
                            }
                            else -> {
                                data.children.forEach {
                                    if (it.getValue(Rquest::class.java)!!.uid == getCurrentUid()) {
                                        isFriendEvent.value = FriendRequestEnum.WAITING
                                    } else isFriendEvent.value = FriendRequestEnum.REJECTED
                                }
                            }
                        }
                    }
                })
        }
    }

    fun getCurrentUid() = appRepository.getCurrentUser()!!.uid

    fun removeRequest(userId: String) {
        uiScope.launch {
            appRepository.removeFriendRequestFromSender(userId)
                .addOnSuccessListener {
                    isFriendEvent.value = FriendRequestEnum.REJECTED
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun addFriend(userId: String) {
        uiScope.launch {
            appRepository.sendFriendRequest(userId)
                .addOnSuccessListener {
                    isFriendEvent.value = FriendRequestEnum.WAITING
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }
}
