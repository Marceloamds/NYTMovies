package com.nyt.movies.domain.entity.movie

import java.io.Serializable
import java.lang.Exception

data class MoviesList(
    val status: String,
    var hasMore: Boolean,
    val numResults: Int,
    val movies: List<Movie>,
    var exception: Exception? = null
) : Serializable