package com.karl.last_chat.view.search

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class SearchViewModel(private val appRepository: AppRepository?, application: Application) :
    BaseViewModel(appRepository, application) {

    val eventUserQuery by lazy { SingleLiveEvent<MutableList<User>>() }

    val users = mutableListOf<User>()

    fun queryUserName(userName: String) {
        uiScope.launch {
            appRepository?.queryUserName(userName)
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            users.clear()
                            dataSnapshot.children.forEach {
                                val data = it.getValue(User::class.java)
                                queryInside(userName, data)
                            }
                            eventUserQuery.value = users
                        }
                    }
                })
        }
    }

    fun queryInside(userName: String, data: User?) {
        val curentUserId = appRepository?.getCurrentUser()!!.uid
        if (userName.isEmpty()) users.clear()
        else {
            Log.d("userName", userName + "////" + data?.userName?.trim()?.toLowerCase())
            if (data?.userName?.toLowerCase()?.trim()?.contains(userName.toLowerCase().trim()) == true) {
                if (curentUserId != data.uid) users.add(data)
            }
        }
    }
}