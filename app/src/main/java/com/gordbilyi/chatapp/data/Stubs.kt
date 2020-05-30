package com.gordbilyi.chatapp.data

import com.gordbilyi.chatapp.chats.ChatsViewModel
import java.util.*

class Stubs {
    companion object {

        val CONTACTS = listOf("Steve", "Paul", "Gord", "Nika", "Gareth")

        fun prepopulateDatabase(viewModel: ChatsViewModel) {

            val calendar = Calendar.getInstance()
            calendar.set(2008, 8, 23) // Android release
            calendar.set(Calendar.HOUR_OF_DAY, 12)
            calendar.set(Calendar.MINUTE, 0)
            val time1 = calendar.time
            calendar.add(Calendar.MINUTE, 5)
            val time2 = calendar.time
            calendar.add(Calendar.MINUTE, 5)
            val time3 = calendar.time
            calendar.time = Date()
            calendar.add(Calendar.DATE, -1)
            val time4 = calendar.time
            calendar.add(Calendar.MINUTE, 5)
            val time5 = calendar.time
            calendar.add(Calendar.DATE, -1)
            calendar.set(Calendar.HOUR_OF_DAY, 22)
            val time6 = calendar.time
            calendar.add(Calendar.MINUTE, 20)
            val time7 = calendar.time
            calendar.time = Date()
            calendar.add(Calendar.MINUTE, -1)
            val time8 = calendar.time
            calendar.add(Calendar.MINUTE, 1)
            val time9 = calendar.time

            viewModel.createMessages(
                    Message(1, "Have you seen this?", isLocal = true, createdAt = time1),
                    Message(1, "Yeah, it looks very promising!", isLocal = false, createdAt = time2),
                    Message(1, "I don't think so.", isLocal = true, createdAt = time3),
                    Message(2, "Hey Jude!", isLocal = false, createdAt = time4),
                    Message(3, "How's you quarantine going?", isLocal = true, createdAt = time4),
                    Message(3, "Tired of it.", isLocal = false, createdAt = time5),
                    Message(4, "Do you have any ideas for Victor's birthday gift?", isLocal = false, createdAt = time6),
                    Message(4, "How about that psychology book I told you the other day? He's gonna love it!", isLocal = true, createdAt = time7),
                    Message(5, "The webinar is about to start, are you joining?", isLocal = true, createdAt = time8),
                    Message(5, "5 min", isLocal = false, createdAt = time9))
        }
    }
}