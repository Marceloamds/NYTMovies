package com.nyt.movies.domain.entity.movie

import java.io.Serializable
import java.lang.Exception

data class MoviesList(
    var hasMore: Boolean,
    val movies: List<Movie>,
    var exception: Exception? = null
) : Serializable