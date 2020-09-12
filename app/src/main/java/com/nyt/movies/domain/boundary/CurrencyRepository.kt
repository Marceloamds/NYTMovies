package com.nyt.movies.domain.boundary

import com.nyt.movies.domain.entity.movie.MoviesList

interface CurrencyRepository {

    suspend fun getMoviesList(page: Int): MoviesList?
}