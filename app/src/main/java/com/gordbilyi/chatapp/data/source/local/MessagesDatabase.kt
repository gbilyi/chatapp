package com.gordbilyi.chatapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gordbilyi.chatapp.data.Message

/**
 * The Room Database that contains the Message table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Message::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MessagesDatabase : RoomDatabase() {

    abstract fun messageDao(): MessagesDao

}
