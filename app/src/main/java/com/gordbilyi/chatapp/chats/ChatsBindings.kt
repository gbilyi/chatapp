package com.gordbilyi.chatapp.chats

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gordbilyi.chatapp.R
import com.gordbilyi.chatapp.data.Message
import com.gordbilyi.chatapp.data.Stubs.Companion.CONTACTS
import com.gordbilyi.chatapp.util.DateUtils.Companion.DATE_FORMAT_FULL_DATE
import com.gordbilyi.chatapp.util.DateUtils.Companion.DATE_FORMAT_JUST_TIME
import com.gordbilyi.chatapp.util.DateUtils.Companion.isYesterday
import java.util.*

@BindingAdapter("app:chatItems")
fun setItems(listView: RecyclerView, items: List<Message>?) {
    items?.let {
        (listView.adapter as ChatsAdapter).submitList(items)
    }
}

@BindingAdapter("app:formattedDate")
fun setFormattedDate(textView: TextView, date: Date?) {
    date?.let {
        when {
            DateUtils.isToday(date.time) -> {
                textView.text = DATE_FORMAT_JUST_TIME.format(date)
            }
            isYesterday(date) -> {
                textView.text = textView.context.getString(R.string.yesterday)
            }
            else -> {
                textView.text = DATE_FORMAT_FULL_DATE.format(date)
            }
        }
    }
}

@BindingAdapter("app:contactName")
fun getContactNameById(textView: TextView, contactId: Int) {
    textView.text = CONTACTS[contactId - 1]
}
