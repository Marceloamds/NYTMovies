package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiMoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reviews/all.json")
    suspend fun getCurrencyList(
        @Query("offset") offset: Int
    ): Response<ApiMoviesList>
}