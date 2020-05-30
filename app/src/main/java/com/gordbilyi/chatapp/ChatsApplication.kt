package com.gordbilyi.chatapp

import android.app.Application
import com.gordbilyi.chatapp.data.source.MessagesRepository
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * An application that lazily provides a repository.
 **/
class ChatsApplication : Application() {

    val messagesRepository: MessagesRepository
        get() = ServiceLocator.provideMessagesRepository(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}
