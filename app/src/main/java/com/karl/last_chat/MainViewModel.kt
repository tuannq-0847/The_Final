package com.karl.last_chat

import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.repository.AppRepository
import kotlinx.coroutines.runBlocking

class MainViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    fun updateLocation(lat: Double, long: Double) {
        runBlocking {
            appRepository.updateLocation(lat, long)
        }
    }
}
