package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiCurrencyList
import com.nyt.movies.data.remote.entity.ApiCurrentQuotes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("reviews/all.json")
    suspend fun getCurrencyList(): Response<ApiCurrencyList>

    @GET("live")
    suspend fun getCurrentQuotes(): Response<ApiCurrentQuotes>
}