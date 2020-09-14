package com.nyt.movies.data.repository

import com.nyt.movies.data.local.dao.MovieDao
import com.nyt.movies.data.local.entity.DbMovie
import com.nyt.movies.data.remote.client.ApiClient
import com.nyt.movies.data.util.request.containsQuery
import com.nyt.movies.domain.boundary.MovieRepository
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.MoviesList

class DefaultMovieRepository constructor(
    private val apiClient: ApiClient,
    private val movieDao: MovieDao
) : MovieRepository {

    override suspend fun getMoviesList(page: Int, query: String): MoviesList? {
        var moviesList = getMoviesFromDatabase(page, query)
        if (!moviesList.hasMore) { moviesList = updateDatabaseWithApi(page, query) }
        return moviesList
    }

    override suspend fun likeMovie(movie: Movie): Movie {
        movie.isFavorite = !movie.isFavorite
        movieDao.updateMovie(DbMovie.fromDomainObject(movie))
        return movie
    }

    override suspend fun getFavoriteMoviesList(page: Int): MoviesList? {
        val movieList = movieDao.getFavoriteMovies(page.localPage())
        return MoviesList(
            movieDao.getFavoriteMoviesCount() > movieList.size,
            movieList.map { it.toDomainObject() }
        )
    }

    private suspend fun getMoviesFromDatabase(page: Int, query: String): MoviesList {
        val movieList = movieDao.getMovies(page.localPage(), query.containsQuery())
        return MoviesList(
            movieDao.getMoviesCount(query.containsQuery()) > movieList.size,
            movieList.map { it.toDomainObject() }
        )
    }

    private suspend fun updateDatabaseWithApi(page: Int, query: String): MoviesList {
        var hasMoreApiMovies = false
        var apiException: Exception? = null
        try {
            val apiMoviesList = apiClient.getMoviesList(page.apiPage(), query)?.toDomainObject()
            apiMoviesList?.movies?.let { movieDao.insertMovies(it.map(DbMovie::fromDomainObject)) }
            hasMoreApiMovies = apiMoviesList?.hasMore ?: false
        } catch (e: Exception) {
            apiException = e
        }
        return getMoviesFromDatabase(page, query).apply {
            hasMore = hasMoreApiMovies
            exception = apiException
        }
    }

    private fun Int.localPage() = (this + 1) * MOVIES_PER_PAGE
    private fun Int.apiPage() = this * MOVIES_PER_PAGE

    companion object {
        private const val MOVIES_PER_PAGE = 20
    }
}