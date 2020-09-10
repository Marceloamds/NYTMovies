package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiCurrencyList
import com.nyt.movies.data.remote.entity.ApiCurrentQuotes
import com.nyt.movies.data.util.request.RequestHandler

class ApiClient constructor(
    private val apiService: ApiService
) : RequestHandler() {

    suspend fun getCurrencyList(): ApiCurrencyList? {
        return makeRequest(apiService.getCurrencyList())
    }

    suspend fun getCurrentQuotes(): ApiCurrentQuotes? {
        return makeRequest(apiService.getCurrentQuotes())
    }
}