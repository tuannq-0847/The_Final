package com.karl.last_chat.view.register_flow.gender

import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.model.User
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class GenderViewModel(private val appRepository: AppRepository) : BaseViewModel(appRepository) {

    val completeEvent by lazy { SingleLiveEvent<Boolean>() }

    fun insertUser(user: User) {
        uiScope.launch {
            appRepository.insertUser(
                user
            ).addOnSuccessListener {
                completeEvent.value = true
            }.addOnFailureListener {
                error.value = it
            }
        }
    }

    fun getCurrentUId() = appRepository.getCurrentUser()!!.uid
}
