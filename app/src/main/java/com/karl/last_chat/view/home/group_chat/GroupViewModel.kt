package com.karl.last_chat.view.home.group_chat

import android.app.Application
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.R
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.FriendRequestEnum
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GroupViewModel(private val appRepository: AppRepository, private val app: Application) :
    BaseViewModel(appRepository, app) {
    val friends by lazy { SingleLiveEvent<MutableList<User>>() }
    val friendsData = mutableListOf<User>()
    val friendRequestEvent by lazy { SingleLiveEvent<FriendRequestEnum>() }

    fun getFriendRequest() {
        uiScope.launch {
            appRepository.getFriendRequest().addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    error.value = p0.toException()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        p0.children.forEach {
                            val userId = it.getValue(String::class.java)
                            getInforUserId(userId!!)
                        }
                    }
                }

            })
        }
    }

    fun getInforUserId(userId: String) {
        uiScope.launch {
            appRepository.getInforUser(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        friendsData.add(p0.getValue(User::class.java)!!)
                        friends.value = friendsData
                    }
                })
        }
    }

    fun setDiscussId(userId: String, discussId: String): String {
        uiScope.launch {
            appRepository.setIdDiscuss(userId, discussId)
        }
        return discussId
    }

    fun acceptFriend(userId: String, discussId: String) {
        uiScope.launch {
            appRepository.removeFriendRequest(userId)
                .addOnSuccessListener {
                    friendRequestEvent.value = FriendRequestEnum.ACCEPTED
                    runBlocking {
                        appRepository.sendMessage(
                            setDiscussId(userId, discussId),
                            Message(
                                content = app.applicationContext.getString(R.string.notice_connect),
                                idUserSend = appRepository.getCurrentUser()!!.uid,
                                idUserRec = userId,
                                seen = appRepository.getCurrentUser()!!.uid
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun rejectFriend(userId: String) {
        uiScope.launch {
            appRepository.removeFriendRequest(userId)
                .addOnSuccessListener {
                    friendRequestEvent.value = FriendRequestEnum.REJECTED
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }
}
