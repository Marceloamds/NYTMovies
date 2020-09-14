package com.nyt.movies.data.remote.client

import com.nyt.movies.data.remote.entity.ApiMoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reviews/search.json")
    suspend fun getMoviesList(
        @Query("offset") offset: Int,
        @Query("query") query: String
    ): Response<ApiMoviesList>
}