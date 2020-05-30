package com.gordbilyi.chatapp.data.source

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gordbilyi.chatapp.data.Message
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Implementation of a data source with static access to the data for easy testing.
 */
class FakeRepository : MessagesRepository {

    private var data: LinkedHashMap<String, Message> = LinkedHashMap()

    private val observableMessages = MutableLiveData<List<Message>>()

    override suspend fun saveMessages(vararg messages: Message) {
        for(message in messages) {
            data[message.id] = message
        }
    }

    override fun observeMessages(): LiveData<List<Message>> {
        runBlocking { getMessages() }
        return observableMessages
    }

    override fun observeMessagesForContact(contactId: Int): LiveData<List<Message>> {
        runBlocking { getMessages() }
        return observableMessages
    }

    private fun getMessages(){
        observableMessages.value = data.values.toList()
    }

    @VisibleForTesting
    fun addMessages(vararg messages: Message) {
        for (message in messages) {
            data[message.id] = message
        }
        runBlocking { getMessages() }
    }

}
