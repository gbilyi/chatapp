package com.gordbilyi.chatapp.data.source

import androidx.lifecycle.LiveData
import com.gordbilyi.chatapp.data.Message

/**
 * Interface to the data layer.
 */
interface MessagesRepository {

    fun observeMessages(): LiveData<List<Message>>
    fun observeMessagesForContact(contactId: Int): LiveData<List<Message>>

    suspend fun saveMessages(vararg messages: Message)

}
