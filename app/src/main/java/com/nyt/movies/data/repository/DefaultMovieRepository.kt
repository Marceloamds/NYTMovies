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
        var databaseMoviesList = getMoviesFromDatabase(page, query)
        if (!databaseMoviesList.hasMore) {
            databaseMoviesList = updateDatabaseWithApi(page, query)
        }
        return databaseMoviesList
    }

    override suspend fun likeMovie(movie: Movie): Movie {
        return movie
    }

    private suspend fun getMoviesFromDatabase(page: Int, query: String): MoviesList {
        val movieList = movieDao.getMovies(page.localPage(), query.containsQuery())
        return MoviesList(
            "OK",
            movieDao.getMoviesCount(query.containsQuery()) > movieList.size,
            movieList.size,
            movieList.map { it.toDomainObject() }
        )
    }

    private suspend fun updateDatabaseWithApi(page: Int, query: String): MoviesList {
        var hasMoreApiMovies = false
        var apiException: Exception? = null
        tryCatch({ apiException = it }) {
            val apiMoviesList = apiClient.getMoviesList(page.apiPage(), query)?.toDomainObject()
            apiMoviesList?.movies?.let { movieDao.insertMovies(it.map(DbMovie::fromDomainObject)) }
            hasMoreApiMovies = apiMoviesList?.hasMore ?: false
        }
        return getMoviesFromDatabase(page, query).apply {
            hasMore = hasMoreApiMovies
            exception = apiException
        }
    }

    private suspend fun tryCatch(exceptionHandler: (Exception) -> Unit, block: suspend () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            exceptionHandler(e)
        }
    }

    private fun Int.localPage() = (this + 1) * MOVIES_PER_PAGE
    private fun Int.apiPage() = this * MOVIES_PER_PAGE

    companion object {
        private const val MOVIES_PER_PAGE = 20
    }
}