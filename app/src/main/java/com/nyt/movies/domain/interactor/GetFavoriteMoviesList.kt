package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.boundary.MovieRepository

class GetFavoriteMoviesList constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(page: Int) = movieRepository.getFavoriteMoviesList(page)
}