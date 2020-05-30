package com.gordbilyi.chatapp.chats

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.gordbilyi.chatapp.R
import com.gordbilyi.chatapp.ServiceLocator
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.Stubs.Companion.CONTACTS
import com.gordbilyi.chatapp.data.source.FakeRepository
import com.gordbilyi.chatapp.data.source.MessagesRepository
import com.gordbilyi.chatapp.util.saveMessagesBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.Calendar.MINUTE

/**
 * Integration test for the Chats List screen.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class ChatsFragmentTest {

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
    fun showChats() {

        val cal = Calendar.getInstance()
        val time1 = cal.time
        cal.add(MINUTE, 5)
        val time2 = cal.time

        // GIVEN - Two messages already in the repository
        repository.saveMessagesBlocking(
                Message(1, "Message1", isLocal = false, createdAt = time1),
                Message(2, "Message2", isLocal = false, createdAt = time2))

        // WHEN - On startup
        launchActivity()

        // THEN - Verify messages are displayed on screen
        onView(withText("Message1")).check(matches(isDisplayed()))
        onView(withText("Message2")).check(matches(isDisplayed()))

        onView(withText(CONTACTS[0])).check(matches(isDisplayed()))
        onView(withText(CONTACTS[1])).check(matches(isDisplayed()))

    }


    private fun launchActivity(): ActivityScenario<ChatsActivity>? {
        val activityScenario = launch(ChatsActivity::class.java)
        activityScenario.onActivity { activity ->
            // Disable animations in RecyclerView
            (activity.findViewById(R.id.chats_list) as RecyclerView).itemAnimator = null
        }
        return activityScenario
    }

}
