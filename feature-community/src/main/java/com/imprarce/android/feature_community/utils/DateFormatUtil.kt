package com.imprarce.android.feature_community.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatUtil {
    companion object {

        fun formatDate(inputDate: Date?): String {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(inputDate.toString())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return outputFormat.format(date)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): LocalDateTime {
            return LocalDateTime.now()
        }
    }
}