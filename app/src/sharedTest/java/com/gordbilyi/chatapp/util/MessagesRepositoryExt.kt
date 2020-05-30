package com.gordbilyi.chatapp.util

import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.MessagesRepository
import kotlinx.coroutines.runBlocking

/**
 * A blocking version of MessagesRepository.saveMessages to minimize the number of times we have to
 * explicitly add <code>runBlocking { ... }</code> in our tests
 */
fun MessagesRepository.saveMessagesBlocking(vararg messages: Message) = runBlocking {
    this@saveMessagesBlocking.saveMessages(*messages)
}
