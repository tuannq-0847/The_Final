package com.karl.last_chat

import android.app.Application
import android.content.Context
import com.crashlytics.android.answers.ContentViewEvent
import com.karl.last_chat.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.logging.Logger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModules)
        }
    }

    companion object {
        lateinit var context: Context
    }
}
