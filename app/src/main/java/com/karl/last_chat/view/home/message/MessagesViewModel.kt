package com.karl.last_chat.view.home.message

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.LastMessage
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.runBlocking

class MessagesViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {
    val messageEvents by lazy { SingleLiveEvent<MutableList<LastMessage>>() }
    val lastMessages = mutableListOf<LastMessage>()

    fun getMessagesList() {
        runBlocking {
            // showLoading()
            appRepository.getMessages()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val idDiscuss = dataSnapshot.getValue(String::class.java)
                        idDiscuss?.let {
                            getLastMessage(it)
                        }
                    }
                })
        }
    }

    fun getLastMessage(idDiscuss: String) {
        runBlocking {
            appRepository.getDisscussMessages(idDiscuss)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        if (dataSnapshot.exists()) {

                        }
                    }
                })
        }
    }
}
