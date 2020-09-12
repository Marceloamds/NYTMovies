package com.nyt.movies.presentation.util.extension

import com.nyt.movies.presentation.util.constants.DAY_MONTH_YEAR
import java.text.SimpleDateFormat
import java.util.*

fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun <T> List<T>.adding(elements: List<T>?): List<T> {
    val mutableList = toMutableList()
    elements?.let { mutableList.addAll(elements) }
    return mutableList.toList()
}

fun Date.format(pattern: String =  DAY_MONTH_YEAR): String {
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
