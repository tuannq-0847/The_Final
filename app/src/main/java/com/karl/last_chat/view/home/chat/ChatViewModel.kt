package com.karl.last_chat.view.home.chat

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ChatViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {

    val isSend by lazy { SingleLiveEvent<Message>() }
    val idDiscuss by lazy { SingleLiveEvent<String>() }
    val messages = SingleLiveEvent<MutableList<Message>>()
    val a = mutableListOf<Message>()
    val userEvent by lazy { SingleLiveEvent<User>() }

    fun getCurrentUser() = appRepository.getCurrentUser()

    fun setDisscussId(userId: String, dId: String) {
        uiScope.launch {
            appRepository.setIdDiscuss(userId, dId)
                .addOnCompleteListener {
                    idDiscuss.value = dId
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun getDisscussId(userId: String) {
        uiScope.launch {
            appRepository.getIdDiscuss(userId).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    error.value = p0.toException()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("dataDisscuss", p0.value.toString())
                    if (!p0.exists()) {
                        idDiscuss.value = ""
                    } else {
                        idDiscuss.value = p0.getValue(String::class.java)
                    }
                }

            })
        }
    }

    fun getMesages(idDiscuss: String) {
        Log.d("idDiscuss", idDiscuss)
        uiScope.launch {
            appRepository.getDisscussMessages(idDiscuss)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        a.clear()
                        Log.d("dataz", p0.childrenCount.toString())
                        p0.children.forEach {
                            it.getValue(Message::class.java)?.let { m ->
                                Log.d("dataz", m.toString())
                                a.add(m)
                            }
                        }
                        messages.value = a
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                })
        }
    }

    fun sendMessage(idDiscuss: String, message: Message) {
        uiScope.launch {
            appRepository.sendMessage(idDiscuss, message)
                .addOnSuccessListener {
                    isSend.value = message
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun saveNotification(receiveId: String, notification: Notification) {
        uiScope.launch {
            appRepository.saveNotification(receiveId, notification)
                .addOnSuccessListener {

                }
                .addOnFailureListener {
                    error.value = it
                }
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
                        Log.d("dataSnapshot", p0.childrenCount.toString())
                    }
                })
        }
    }
}
