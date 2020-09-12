package com.nyt.movies.presentation.util.extension

fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun <T> List<T>.adding(elements: List<T>?): List<T> {
    val mutableList = toMutableList()
    elements?.let { mutableList.addAll(elements) }
    return mutableList.toList()
}
