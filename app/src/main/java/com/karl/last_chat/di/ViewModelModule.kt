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
import com.karl.last_chat.view.profile.ProfileViewModel
import com.karl.last_chat.view.profile.detail_image.DetailImageViewModel
import com.karl.last_chat.view.profile.edit.EditProfileFragment
import com.karl.last_chat.view.profile.edit.EditProfileViewModel
import com.karl.last_chat.view.register.RegisterViewModel
import com.karl.last_chat.view.register_flow.birthday.BirthdayViewModel
import com.karl.last_chat.view.register_flow.first_name.MyFirstNameViewModel
import com.karl.last_chat.view.register_flow.gender.GenderViewModel
import com.karl.last_chat.view.register_flow.parent_res.ParentResViewModel
import com.karl.last_chat.view.register_flow.welcome.ResWelcomeViewModel
import com.karl.last_chat.view.search.SearchViewModel
import com.karl.last_chat.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { ChatViewModel(get(), get()) }
    viewModel { PersonalViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { MessagesViewModel(get(), get()) }
    viewModel { GroupViewModel(get(), get()) }
    viewModel { DiscoveryViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ParentResViewModel(get(), get()) }
    viewModel { ResWelcomeViewModel(get(), get()) }
    viewModel { BirthdayViewModel(get(), get()) }
    viewModel { MyFirstNameViewModel(get(), get()) }
    viewModel { GenderViewModel(get(), get()) }
    viewModel { DetailImageViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }
}
