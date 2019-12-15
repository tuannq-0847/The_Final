package com.karl.last_chat.di

import com.google.firebase.auth.FirebaseAuth
import com.karl.last_chat.base.BaseViewModel
import com.karl.last_chat.view.auth.AuthViewModel
import com.karl.last_chat.view.home.HomeViewModel
import com.karl.last_chat.view.home.chat.ChatViewModel
import com.karl.last_chat.view.login.LoginViewModel
import com.karl.last_chat.view.register.RegisterViewModel
import com.karl.last_chat.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { AuthViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { ChatViewModel() }
    viewModel { SplashViewModel() }
    single { FirebaseAuth.getInstance() }
}
