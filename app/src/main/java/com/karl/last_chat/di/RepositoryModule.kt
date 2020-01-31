package com.karl.last_chat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.karl.last_chat.data.repository.AppRepository
import com.karl.last_chat.data.repository.impl.AppRepositoryImpl
import com.karl.last_chat.data.repository.paging.MessageDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseDatabase.getInstance() }
    single { FirebaseInstanceId.getInstance() }
    single { FirebaseStorage.getInstance() }
    single<AppRepository> { AppRepositoryImpl(get(), get(), get(), get()) }
    single { MessageDataSource(get()) }
}
