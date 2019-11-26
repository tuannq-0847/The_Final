package com.karl.last_chat.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

//singleton
val appModule = module {
    single { androidApplication().resources }
}

val appModules = listOf(appModule)
