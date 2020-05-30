package com.gordbilyi.chatapp.chats

import androidx.lifecycle.*
import com.gordbilyi.chatapp.Event
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.Stubs
import com.gordbilyi.chatapp.data.source.MessagesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the chat list screen.
 */
class ChatsViewModel(
        private val messagesRepository: MessagesRepository
) : ViewModel() {

    fun createMessages(vararg newMessages: Message) = viewModelScope.launch {
        messagesRepository.saveMessages(*newMessages)
    }

    private val _openChatEvent = MutableLiveData<Event<Int>>()
    val openChatEvent: LiveData<Event<Int>> = _openChatEvent

    /**
     * Called by Data Binding.
     */
    fun openChat(contactId: Int) {
        _openChatEvent.value = Event(contactId)
    }


    private val _items: LiveData<List<Message>> = messagesRepository.observeMessages()
            .distinctUntilChanged().switchMap {
                if (it.isEmpty()) {
                    Stubs.prepopulateDatabase(this)
                }
                MutableLiveData(it)
            }

    val items: LiveData<List<Message>> = _items

}
