package com.nyt.movies.data.util.request

fun String.containsQuery(): String {
    return "%$this%"
}