package com.karl.last_chat.view.home.message

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
    val lastMessages = arrayListOf<LastMessage>()

    fun getMessagesList() {
        runBlocking {
            // showLoading()
            appRepository.getMessages()
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
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
        if (lastMessages.isEmpty()) lastMessages.add(lastMessage)
        else
            for (i in 0 until lastMessages.size) {
                if (lastMessages[i].idDiscuss == lastMessage.idDiscuss) {
                    lastMessages[i] = lastMessage
                    break
                } else {
                    lastMessages.add(lastMessage)
                }
            }
    }
}
