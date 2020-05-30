package com.gordbilyi.chatapp.data.source

import androidx.lifecycle.LiveData
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.local.MessagesDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Default implementation of [MessagesRepository]. Single entry point for managing messages' data.
 */
class DefaultMessagesRepository(private val messagesLocalDataSource: MessagesDataSource) : MessagesRepository {

    override fun observeMessages(): LiveData<List<Message>> {
        return messagesLocalDataSource.observeMessages()
    }

    override fun observeMessagesForContact(contactId: Int): LiveData<List<Message>> {
        return messagesLocalDataSource.observeMessagesForContact(contactId)
    }

    override suspend fun saveMessages(vararg messages: Message) {
        coroutineScope {
            launch { messagesLocalDataSource.saveMessages(*messages) }
        }
    }


}
