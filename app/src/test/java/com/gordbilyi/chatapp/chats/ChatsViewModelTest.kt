package com.gordbilyi.chatapp.chats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.gordbilyi.chatapp.MainCoroutineRule
import com.gordbilyi.chatapp.assertLiveDataEventTriggered
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.FakeRepository
import com.gordbilyi.chatapp.getOrAwaitValue
import com.gordbilyi.chatapp.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [ChatsViewModel]
 */
class ChatsViewModelTest {

    // Subject under test
    private lateinit var chatsViewModel: ChatsViewModel

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

        chatsViewModel = ChatsViewModel(messagesRepository)
    }

    @Test
    fun loadMessagesFromRepository() {
        chatsViewModel.items.observeForTesting {
            assertThat(chatsViewModel.items.getOrAwaitValue()).hasSize(2)
        }
    }

    @Test
    fun clickOnOpenChatDetial_setsEvent() {
        // When opening chat details
        val contactId = 1
        chatsViewModel.openChat(contactId)

        // Then the event is triggered
        assertLiveDataEventTriggered(chatsViewModel.openChatEvent, contactId)
    }


}