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
    val messageEvents by lazy { SingleLiveEvent<ArrayList<LastMessage>>() }
    val eventReload by lazy { SingleLiveEvent<Boolean>() }
    val lastMessages = arrayListOf<LastMessage>()

    fun getMessagesList() {
        runBlocking {
            // showLoading()
            appRepository.getMessages()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (!dataSnapshot.exists()) messageEvents.value = arrayListOf()
                        dataSnapshot.children.forEach {
                            val idDiscuss = it.getValue(String::class.java)
                            idDiscuss?.let { id ->
                                getLastMessage(id)
                            }
                        }
                    }
                })
        }
    }

    fun getCurrentUId() = appRepository.getCurrentUser()!!.uid

    fun getLastMessage(idDiscuss: String) {
        runBlocking {
            appRepository.getDisscussMessages(idDiscuss)
                .orderByKey().limitToLast(1)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataSnapshot.children.forEach {
                                val message = it.getValue(Message::class.java)
                                message?.let {
                                    getDetailLastMessage(it, idDiscuss)
                                }
                            }
                        }
                    }
                })
        }
    }

    fun getDetailLastMessage(message: Message, idDiscuss: String) {
        uiScope.launch {
            appRepository.getInforUser(if (getCurrentUId() == message.idUserRec) message.idUserSend else message.idUserRec)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        user?.let {
                            handleList(
                                LastMessage(
                                    lastContent = message.content,
                                    nameSender = it.userName,
                                    pathImage = it.pathAvatar,
                                    seen = message.seen,
                                    onlineStatus = it.online,
                                    idUserRec = message.idUserRec,
                                    idUserSend = message.idUserSend,
                                    idDiscuss = idDiscuss
                                )
                            )
                            messageEvents.value = lastMessages
                        }
                    }
                })
        }
    }

    private fun handleList(lastMessage: LastMessage) {
        lastMessages.add(lastMessage)
        for (i in 0 until lastMessages.size) {
            if (lastMessages[i].idDiscuss == lastMessage.idDiscuss) {
                lastMessages[i] = lastMessage
                if (lastMessages.count { it == lastMessage } > 1) {
                    lastMessages.removeAt(lastMessages.size - 1)
                }
                break
            }
        }
    }
}
