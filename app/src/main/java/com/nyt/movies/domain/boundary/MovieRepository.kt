package com.nyt.movies.domain.boundary

import com.nyt.movies.domain.entity.movie.MoviesList

interface MovieRepository {

    suspend fun getMoviesList(page: Int, query: String): MoviesList?
}