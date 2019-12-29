package com.karl.last_chat.di

import com.karl.last_chat.MainViewModel
import com.karl.last_chat.view.auth.AuthViewModel
import com.karl.last_chat.view.home.HomeViewModel
import com.karl.last_chat.view.home.chat.ChatViewModel
import com.karl.last_chat.view.home.discovery.DiscoveryViewModel
import com.karl.last_chat.view.home.group_chat.GroupViewModel
import com.karl.last_chat.view.home.message.MessagesViewModel
import com.karl.last_chat.view.login.LoginViewModel
import com.karl.last_chat.view.personal.PersonalViewModel
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
    viewModel { PersonalViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MessagesViewModel(get()) }
    viewModel { GroupViewModel() }
    viewModel { DiscoveryViewModel(get()) }
    viewModel { MainViewModel(get()) }
}
