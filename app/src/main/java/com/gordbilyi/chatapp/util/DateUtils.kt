package com.gordbilyi.chatapp.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        val DATE_FORMAT_FULL_DATE = SimpleDateFormat("yyyy-M-d", Locale.CANADA)
        val DATE_FORMAT_JUST_TIME = SimpleDateFormat("h:mm a", Locale.CANADA)
        fun isYesterday(d: Date): Boolean = DateUtils.isToday(d.time + DateUtils.DAY_IN_MILLIS)
    }
}

