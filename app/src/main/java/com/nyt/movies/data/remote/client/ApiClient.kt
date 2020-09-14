package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiMoviesList
import com.nyt.movies.data.util.request.RequestHandler

class ApiClient constructor(
    private val apiService: ApiService
) : RequestHandler() {

    suspend fun getMoviesList(offset: Int, query: String): ApiMoviesList? {
        return makeRequest(apiService.getMoviesList(offset, query))
    }
}