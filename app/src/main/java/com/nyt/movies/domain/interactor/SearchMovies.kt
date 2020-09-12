package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.boundary.MovieRepository

class SearchMovies constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(name: String) = movieRepository.searchMovies(name)
}