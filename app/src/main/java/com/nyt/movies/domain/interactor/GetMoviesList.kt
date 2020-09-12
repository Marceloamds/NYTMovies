package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.boundary.MovieRepository

class GetMoviesList constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(page: Int) = movieRepository.getMoviesList(page)
}