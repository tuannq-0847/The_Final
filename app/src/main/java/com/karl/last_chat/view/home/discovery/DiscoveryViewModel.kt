package com.karl.last_chat.view.home.discovery

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DiscoveryViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {
    val userAroundHere by lazy { SingleLiveEvent<MutableList<User>>() }

    fun getUsers() {
        uiScope.launch {
            appRepository.getUsers()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(data: DataSnapshot) {
                        val users = mutableListOf<User>()
                        data.children.forEach {
                            it.getValue(User::class.java)?.let { u ->
                                if (u.uid != appRepository.getCurrentUser()?.uid)
                                    users.add(u)
                            }
                        }
                        userAroundHere.value = users
                    }
                })
        }
    }
}