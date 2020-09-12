package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiMoviesList
import com.nyt.movies.data.util.request.RequestHandler

class ApiClient constructor(
    private val apiService: ApiService
) : RequestHandler() {

    suspend fun getMoviesList(offset: Int): ApiMoviesList? {
        return makeRequest(apiService.getCurrencyList(offset))
    }

    suspend fun searchMovies(name: String): ApiMoviesList? {
        return makeRequest(apiService.searchMovies(name))
    }
}