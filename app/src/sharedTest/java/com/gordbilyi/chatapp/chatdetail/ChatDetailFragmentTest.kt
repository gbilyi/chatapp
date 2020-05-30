package com.gordbilyi.chatapp.chatdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.gordbilyi.chatapp.R
import com.gordbilyi.chatapp.ServiceLocator
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.source.FakeRepository
import com.gordbilyi.chatapp.data.source.MessagesRepository
import com.gordbilyi.chatapp.util.saveMessagesBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for the Chat Details screen.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ChatDetailFragmentTest {

    private lateinit var repository: MessagesRepository

    @Before
    fun initRepository() {
        repository = FakeRepository()
        ServiceLocator.messagesRepository = repository
    }

    @After
    fun cleanupDb() = runBlockingTest {
        ServiceLocator.messagesRepository = null
    }

    @Test
    fun chatDetails_DisplayedInUi() {
        // GIVEN - Add a couple messages to the DB
        repository.saveMessagesBlocking(
                Message(1, "Message1", isLocal = false),
                Message(1, "Message2", isLocal = true))

        // WHEN - Details fragment launched to display task
        val bundle = ChatDetailFragmentArgs(1).toBundle()
        launchFragmentInContainer<ChatDetailFragment>(bundle, R.style.AppTheme)

        // THEN - list of messages are displayed on the screen
        // make sure that payload from the messages is displayed
        onView(withText("Message1")).check(matches(isDisplayed()))
        onView(withText("Message2")).check(matches(isDisplayed()))

        // make sure date separator is shown
        onView(allOf(withId(R.id.date_separator),
                withEffectiveVisibility(Visibility.VISIBLE)))
                .check(matches(isCompletelyDisplayed()))
                .check(matches(withText(R.string.today)))


    }


}
