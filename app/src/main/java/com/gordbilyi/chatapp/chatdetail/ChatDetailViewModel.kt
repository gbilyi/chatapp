package com.gordbilyi.chatapp.chatdetail

import androidx.lifecycle.*
import com.gordbilyi.chatapp.Event
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.MessagesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the Details screen.
 */
class ChatDetailViewModel(
    private val messagesRepository: MessagesRepository
) : ViewModel() {

    fun start(contactId: Int?) {
        _contactId.value = contactId
    }

    fun sendMessage(contactId: Int, payload: String, isLocal: Boolean = true) {

        if (payload.isBlank()){
            return
        }

        val message = Message(contactId, payload, isLocal)
        viewModelScope.launch {
            messagesRepository.saveMessages(message)
        }

        clearSentMessage()
    }

    private val _clearSentMessageEvent = MutableLiveData<Event<Unit>>()
    val clearSentMessageEvent: LiveData<Event<Unit>> = _clearSentMessageEvent

    private val _contactId = MutableLiveData<Int>()
    private val _messageList = _contactId.switchMap { contactId ->
        messagesRepository.observeMessagesForContact(contactId)
    }
    val messageList: LiveData<List<Message>> = _messageList

    private fun clearSentMessage() {
        _clearSentMessageEvent.value = Event(Unit)
    }

}
