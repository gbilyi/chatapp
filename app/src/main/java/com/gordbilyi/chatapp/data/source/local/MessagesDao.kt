package com.gordbilyi.chatapp.data.source.local

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gordbilyi.chatapp.data.Message

/**
 * Data Access Object for the messages table.
 */
@Dao
interface MessagesDao {

    /**
     * Observes list of messages.
     *
     * @return list of messages where each item is the most recent message for a particular contact.
     */
    @Query("SELECT * FROM messages GROUP BY contact_id HAVING max(created_at) ORDER BY created_at DESC")
    fun observeMessages(): LiveData<List<Message>>

    /**
     * Observes list of messages.
     *
     * @return list of messages for a specific contact
     */
    @Query("SELECT * FROM messages WHERE contact_id=:contactId")
    fun observeMessagesForContact(contactId: Int): LiveData<List<Message>>

    /**
     * Insert a messages in the database.
     *
     * @param message the list of messages to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(vararg message: Message)


    @VisibleForTesting
    @Query("SELECT * FROM messages GROUP BY contact_id HAVING max(created_at) ORDER BY created_at DESC")
    suspend fun getMessages(): List<Message>

    @VisibleForTesting
    @Query("SELECT * FROM messages WHERE contact_id=:contactId")
    suspend fun getMessagesForContact(contactId: Int): List<Message>

}
