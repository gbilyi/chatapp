package com.gordbilyi.chatapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.gordbilyi.chatapp.data.source.DefaultMessagesRepository
import com.gordbilyi.chatapp.data.source.MessagesRepository
import com.gordbilyi.chatapp.data.source.local.MessagesDataSource
import com.gordbilyi.chatapp.data.source.local.MessagesLocalDataSource
import com.gordbilyi.chatapp.data.source.local.MessagesDatabase

/**
 * A Service Locator for the [MessagesRepository].
 */
object ServiceLocator {

    private var database: MessagesDatabase? = null

    @Volatile
    var messagesRepository: MessagesRepository? = null
        @VisibleForTesting set

    fun provideMessagesRepository(context: Context): MessagesRepository {
        synchronized(this) {
            return messagesRepository
                    ?: messagesRepository
                    ?: createMessagesRepository(context)
        }
    }

    private fun createMessagesRepository(context: Context): MessagesRepository {
        val newRepo = DefaultMessagesRepository(createMessagesLocalDataSource(context))
        messagesRepository = newRepo
        return newRepo
    }

    private fun createMessagesLocalDataSource(context: Context): MessagesDataSource {
        val database = database
                ?: createDataBase(context)
        return MessagesLocalDataSource(database.messageDao())
    }

    private fun createDataBase(context: Context): MessagesDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            MessagesDatabase::class.java, DB_NAME
        ).build()
        database = result
        return result
    }

}

private const val DB_NAME = "Messages.db"
