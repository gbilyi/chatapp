package com.gordbilyi.chatapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.gordbilyi.chatapp.MainCoroutineRule
import com.gordbilyi.chatapp.data.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MessagesDaoTest {

    private lateinit var database: MessagesDatabase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                MessagesDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertMessagesAndGetMessagesForChatsScreen() = runBlockingTest {

        val calendar = Calendar.getInstance()
        calendar.set(2020, 1, 1)
        val time1 = calendar.time

        calendar.set(2019, 1, 1)
        val time2 = calendar.time

        calendar.set(2018, 1, 1)
        val time3 = calendar.time

        calendar.set(2017, 1, 1)
        val time4 = calendar.time

        calendar.set(2016, 1, 1)
        val time5 = calendar.time

        calendar.set(2015, 1, 1)
        val time6 = calendar.time

        // GIVEN - insert a messages
        database.messageDao().insertMessages(
                Message(1, "payload1", isLocal = false, createdAt = time1),
                Message(1, "payload2", isLocal = false, createdAt = time2),
                Message(2, "payload3", isLocal = false, createdAt = time3),
                Message(3, "payload4", isLocal = false, createdAt = time4),
                Message(4, "payload5", isLocal = true, createdAt = time5),
                Message(4, "payload6", isLocal = false, createdAt = time6)
        )

        val messages = database.messageDao().getMessages()

        // THEN - The loaded data contains the expected values

        //// keep only last message for each chat
        assertThat(messages.size, `is`(4))


        // check content and order
        assertThat(messages[0].contactId, `is`(1))
        assertThat(messages[0].payload, `is`("payload1"))
        assertThat(messages[0].isLocal, `is`(false))
        assertThat(messages[0].createdAt, `is`(time1))

        assertThat(messages[1].contactId, `is`(2))
        assertThat(messages[1].payload, `is`("payload3"))
        assertThat(messages[1].isLocal, `is`(false))
        assertThat(messages[1].createdAt, `is`(time3))

        assertThat(messages[2].contactId, `is`(3))
        assertThat(messages[2].payload, `is`("payload4"))
        assertThat(messages[2].isLocal, `is`(false))
        assertThat(messages[2].createdAt, `is`(time4))

        assertThat(messages[3].contactId, `is`(4))
        assertThat(messages[3].payload, `is`("payload5"))
        assertThat(messages[3].isLocal, `is`(true))
        assertThat(messages[3].createdAt, `is`(time5))

    }

    @Test
    fun insertMessagesAndGetMessagesForChatDetailsScreen() = runBlockingTest {

        val calendar = Calendar.getInstance()
        calendar.set(2020, 1, 1)
        val time1 = calendar.time

        calendar.set(2019, 1, 1)
        val time2 = calendar.time

        calendar.set(2018, 1, 1)
        val time3 = calendar.time

        calendar.set(2017, 1, 1)
        val time4 = calendar.time

        calendar.set(2016, 1, 1)
        val time5 = calendar.time

        calendar.set(2015, 1, 1)
        val time6 = calendar.time

        // GIVEN - insert a messages
        database.messageDao().insertMessages(
                Message(1, "payload1", isLocal = false, createdAt = time1),
                Message(1, "payload2", isLocal = true, createdAt = time2),
                Message(2, "payload3", isLocal = false, createdAt = time3),
                Message(3, "payload4", isLocal = false, createdAt = time4),
                Message(4, "payload5", isLocal = false, createdAt = time5),
                Message(4, "payload6", isLocal = false, createdAt = time6)
        )

        val messages = database.messageDao().getMessagesForContact(1)

        assertThat(messages.size, `is`(2))

        assertThat(messages[0].contactId, `is`(1))
        assertThat(messages[0].payload, `is`("payload1"))
        assertThat(messages[0].isLocal, `is`(false))
        assertThat(messages[0].createdAt, `is`(time1))

        assertThat(messages[1].contactId, `is`(1))
        assertThat(messages[1].payload, `is`("payload2"))
        assertThat(messages[1].isLocal, `is`(true))
        assertThat(messages[1].createdAt, `is`(time2))

    }

}
