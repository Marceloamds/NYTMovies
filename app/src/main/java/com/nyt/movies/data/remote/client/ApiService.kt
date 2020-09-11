package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiMoviesList
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("reviews/all.json")
    suspend fun getCurrencyList(): Response<ApiMoviesList>
}