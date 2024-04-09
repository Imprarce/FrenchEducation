package com.imprarce.android.frencheducation.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtil {
    companion object {

        fun formatDate(inputDate: Date?): String {
            val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(inputDate.toString())
            val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return outputFormat.format(date)
        }
    }
}