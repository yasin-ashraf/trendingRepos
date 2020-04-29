package com.yasin.trendingrepos.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Yasin on 29/4/20.
 */

fun String.dateToFormat(): String {
    var formattedDate = ""
    if (this.isNotEmpty() && this != "null") {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        formattedDate = outputFormat.format(date)
    }
    return formattedDate
}
