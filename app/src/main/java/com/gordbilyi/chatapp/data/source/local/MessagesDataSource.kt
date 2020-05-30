package com.gordbilyi.chatapp.data.source.local

import androidx.lifecycle.LiveData
import com.gordbilyi.chatapp.data.Message

/**
 * Main entry point for accessing messages data.
 */
interface MessagesDataSource {

    fun observeMessages(): LiveData<List<Message>>
    fun observeMessagesForContact(contactId: Int): LiveData<List<Message>>

    suspend fun saveMessages(vararg messages: Message)

}
