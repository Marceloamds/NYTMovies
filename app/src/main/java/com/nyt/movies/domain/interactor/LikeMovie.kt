package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.boundary.MovieRepository
import com.nyt.movies.domain.entity.movie.Movie

class LikeMovie constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun execute(movie: Movie) = movieRepository.likeMovie(movie)
}