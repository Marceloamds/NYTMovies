package com.nyt.movies.presentation.util.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = DAY_MONTH_YEAR): String {
    return getSimpleDateFormatter(pattern).format(this)
}

fun getSimpleDateFormatter(
    pattern: String,
    timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): SimpleDateFormat {
    return SimpleDateFormat(pattern, Locale.getDefault()).also {
        it.timeZone = timeZone
    }
}

private const val DAY_MONTH_YEAR = "dd/MM/yyyy"