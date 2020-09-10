package com.nyt.movies.presentation.util.extension

fun consume(f: () -> Unit): Boolean {
    f()
    return true
}