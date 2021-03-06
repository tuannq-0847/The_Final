package com.karl.last_chat.view.splash

import android.app.Application
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.repository.AppRepository
import kotlinx.coroutines.runBlocking

class SplashViewModel(private val appRepository: AppRepository,application: Application) : BaseViewModel(appRepository,application) {

    fun isLoggedIn(): Boolean = runBlocking { appRepository.isLoggedIn() }
}
