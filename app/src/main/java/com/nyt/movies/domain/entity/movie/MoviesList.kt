package com.nyt.movies.domain.entity.movie

import java.io.Serializable

data class MoviesList(
    val status: String,
    val hasMore: Boolean,
    val numResults: Int,
    val movies: List<Movie>
) : Serializable