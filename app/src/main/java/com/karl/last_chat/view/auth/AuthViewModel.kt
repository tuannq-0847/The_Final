package com.karl.last_chat.view.auth

import android.app.Application
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.data.repository.AppRepository

class AuthViewModel(private val appRepository: AppRepository,application: Application) : BaseViewModel(appRepository,application) {

}