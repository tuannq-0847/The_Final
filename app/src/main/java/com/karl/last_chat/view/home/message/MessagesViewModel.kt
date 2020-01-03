package com.karl.last_chat.view.home.message

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.LastMessage
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessagesViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {
    val messageEvents by lazy { SingleLiveEvent<MutableList<LastMessage>>() }
    val lastMessages = mutableListOf<LastMessage>()

    val userEvent by lazy { SingleLiveEvent<User>() }

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
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.children.forEach {
                                val message = it.getValue(Message::class.java)
                                message?.let {
                                    // lastMessages.add(LastMessage(it.idMessage, it.content, 1, ""))
                                    checkSendUser(it)
                                }
                            }
                            messageEvents.value = lastMessages
                        }
                    }
                })
        }
    }

    fun checkSendUser(message: Message) {
        uiScope.launch {
            val userId = appRepository.getCurrentUser()?.uid
            getInforUser(if (userId == message.idUserSend) message.idUserSend else message.idUserRec)
        }
    }

    fun getInforUser(uid: String) {
        uiScope.launch {
            appRepository.getInforUser(uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        userEvent.value = p0.getValue(User::class.java)
                    }
                })
        }
    }
}
