package com.gordbilyi.chatapp.data.source.local

import androidx.lifecycle.LiveData
import com.gordbilyi.chatapp.data.Message
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class MessagesLocalDataSource internal constructor(
    private val messagesDao: MessagesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessagesDataSource {

    override fun observeMessages(): LiveData<List<Message>> {
        return messagesDao.observeMessages()
    }

    override fun observeMessagesForContact(contactId: Int): LiveData<List<Message>> {
        return messagesDao.observeMessagesForContact(contactId)
    }

    override suspend fun saveMessages(vararg messages: Message) = withContext(ioDispatcher) {
        messagesDao.insertMessages(*messages)
    }

}
