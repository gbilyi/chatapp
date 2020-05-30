package com.gordbilyi.chatapp.chatdetail

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gordbilyi.chatapp.R
import com.gordbilyi.chatapp.data.Message
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:formattedDateSeparator")
fun setFormattedDateSeparator(textView: TextView, date: Date?) {
    date?.let {
        when {
            DateUtils.isToday(date.time) -> {
                textView.text = textView.context.getString(R.string.today)
            }
            com.gordbilyi.chatapp.util.DateUtils.isYesterday(date) -> {
                textView.text = textView.context.getString(R.string.yesterday)
            }
            else -> {
                textView.text = SimpleDateFormat("yyyy-M-d", Locale.CANADA).format(date)
            }
        }
    }
}

@BindingAdapter("app:chatDetailItems")
fun setChatDetailItems(listView: RecyclerView, items: List<Message>?) {
    items?.let {
        (listView.adapter as ChatDetailAdapter).submitList(items)
    }
}

@BindingAdapter("app:formattedTimeOnly")
fun setFormattedDTimeOnly(textView: TextView, date: Date?) {
    date?.let {
        textView.text = SimpleDateFormat("h:mm a", Locale.CANADA).format(date)
    }
}