package com.karl.last_chat.view.home.chat

import android.app.Application
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.Message
import com.karl.last_chat.data.model.Notification
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import com.karl.last_chat.utils.extensions.generateName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import io.grpc.internal.ReadableBuffers.openStream
import java.io.DataInputStream
import java.net.URL


class ChatViewModel(private val appRepository: AppRepository, private val app: Application) :
    BaseViewModel(appRepository, app) {

    val isSend by lazy { SingleLiveEvent<Message>() }
    val idDiscuss by lazy { SingleLiveEvent<String>() }
    val eventUploadImage by lazy { SingleLiveEvent<String>() }
    val messages = SingleLiveEvent<MutableList<Message>>()
    val a = mutableListOf<Message>()
    val userEvent by lazy { SingleLiveEvent<User>() }
    val eventUploadFile by lazy { SingleLiveEvent<String>() }
    val eventDownloadFile by lazy { SingleLiveEvent<Boolean>() }

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

    fun getDisscussId(userId: String, uid: String) {
        uiScope.launch {
            appRepository.getIdDiscuss(userId, uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
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
        uiScope.launch {
            appRepository.getDisscussMessages(idDiscuss)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        a.clear()
                        p0.children.forEach {
                            it.getValue(Message::class.java)?.let { m ->
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
                        userEvent.value = p0.getValue(User::class.java)
                    }
                })
        }
    }

    fun getChildKey(idDiscuss: String, uid: String) {
        uiScope.launch {
            appRepository.getChildStatusSeen(idDiscuss)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        error.value = p0.toException()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.forEach {
                            val message = it.getValue(Message::class.java)
                            Log.d("messageSeen", message!!.seen)
                            updateStatusSeen(idDiscuss, it.key.toString(), uid, message!!.seen)
                        }
                    }

                })
        }
    }

    fun updateStatusSeen(idDiscuss: String, idChild: String, uid: String, seen: String) {
        uiScope.launch {
            Log.d("see", seen)
            appRepository.updateStatusSeen(idDiscuss, idChild, uid, seen)
        }
    }

    fun uploadImage(
        uid: String,
        did: String,
        uri: Uri
    ) {
        uiScope.launch {
            appRepository.uploadImage(uid, did, uri)
                .addOnSuccessListener {
                    eventUploadImage.value = "done"
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun uploadFile(uid: String, did: String, uri: Uri, previewName: String) {
        uiScope.launch {
            appRepository.uploadFileChat(uid, did, uri, previewName)
                .addOnSuccessListener {
                    eventUploadFile.value = "done"
                }
                .addOnFailureListener {
                    error.value = it
                }
        }
    }

    fun downloadFile(url: String, namePreview: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                downloadFile(url, File("".generateName() + namePreview))
                Log.d("fileDownload", app.externalCacheDir?.absolutePath + namePreview)
            }
        }
    }

    private fun downloadFile(url: String, outputFile: File) {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().time)
        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .absolutePath
        val myDir = File(root)
        myDir.mkdirs()

        val fname = "image$timeStamp.png"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        try {
            val u = URL(url)
            val conn = u.openConnection()
            val contentLength = conn.contentLength

            val stream = DataInputStream(u.openStream())

            val buffer = ByteArray(contentLength)
            stream.readFully(buffer)
            stream.close()
            val out = FileOutputStream(file)
            out.write(buffer)
            eventDownloadFile.postValue(true)
            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()
            error.postValue(e)
        }

    }
}
