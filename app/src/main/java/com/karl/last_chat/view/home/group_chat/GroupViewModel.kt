package com.karl.last_chat.view.home.group_chat

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class GroupViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {
    val friends by lazy { SingleLiveEvent<MutableList<User>>() }
    val friendsData = mutableListOf<User>()

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

    fun acceptFriend(userId: String) {

    }

    fun rejectFriend(userId: String) {

    }
}
