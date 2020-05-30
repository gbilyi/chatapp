package com.gordbilyi.chatapp.chatdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.gordbilyi.chatapp.MainCoroutineRule
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.FakeRepository
import com.gordbilyi.chatapp.getOrAwaitValue
import com.gordbilyi.chatapp.observeForTesting
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [ChatDetailViewModel]
 */
class ChatDetailViewModelTest {

    // Subject under test
    private lateinit var chatDetailViewModel: ChatDetailViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var messagesRepository: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {

        messagesRepository = FakeRepository()
        val message1 = Message(1, "Payload1")
        val message2 = Message(2, "Payload2")

        messagesRepository.addMessages(message1, message2)

        chatDetailViewModel = ChatDetailViewModel(messagesRepository)
    }

    @Test
    fun loadMessagesFromRepository() {
        chatDetailViewModel.start(1)
        chatDetailViewModel.messageList.observeForTesting {
            assertThat(chatDetailViewModel.messageList.getOrAwaitValue()).hasSize(2)
        }
    }

    @Test
    fun clickOnSendMessage_setsClearEvent() {
        // When sending a message
        val contactId = 1
        chatDetailViewModel.sendMessage(contactId, "Payload3")

        // Then the event is triggered
        val value = chatDetailViewModel.clearSentMessageEvent.getOrAwaitValue()
        assertNotNull(value.getContentIfNotHandled())
    }

}