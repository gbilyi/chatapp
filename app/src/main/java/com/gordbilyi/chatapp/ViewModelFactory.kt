package com.gordbilyi.chatapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.gordbilyi.chatapp.chatdetail.ChatDetailViewModel
import com.gordbilyi.chatapp.chats.ChatsViewModel
import com.gordbilyi.chatapp.data.source.MessagesRepository


/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
        private val messagesRepository: MessagesRepository,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(ChatsViewModel::class.java) ->
                ChatsViewModel(messagesRepository)
            isAssignableFrom(ChatDetailViewModel::class.java) ->
                ChatDetailViewModel(messagesRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
