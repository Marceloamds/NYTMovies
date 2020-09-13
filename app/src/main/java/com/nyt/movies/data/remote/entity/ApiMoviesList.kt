package com.nyt.movies.data.remote.entity

import com.google.gson.annotations.SerializedName
import com.nyt.movies.domain.entity.movie.MoviesList

data class ApiMoviesList(
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String,
    @SerializedName("has_more") val hasMore: Boolean,
    @SerializedName("num_results") val numResults: Int,
    @SerializedName("results") val results: List<ApiMovie>
) {

    fun toDomainObject(): MoviesList {
        return MoviesList(
            hasMore = hasMore,
            movies = results.map { it.toDomainObject() }
        )
    }
}