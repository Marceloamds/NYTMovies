package com.nyt.movies.data.repository

import com.nyt.movies.data.local.dao.MovieDao
import com.nyt.movies.data.remote.client.ApiClient
import com.nyt.movies.domain.boundary.CurrencyRepository
import com.nyt.movies.domain.entity.movie.MoviesList

class DefaultMovieRepository constructor(
    private val apiClient: ApiClient,
    private val currencyDao: MovieDao
) : CurrencyRepository {

    override suspend fun getMoviesList(): MoviesList? {
       return apiClient.getCurrencyList()?.toDomainObject()
    }

//    private suspend fun getFromDatabase(e: Throwable): MoviesList? {
//        val currencyList = currencyDao.getCurrencies()
//        if (currencyList.isNotEmpty()) {
//            return MoviesList(currencyList.map { it.toDomainObject() })
//        } else {
//            throw e
//        }
//    }
//
//    private suspend fun saveMoviesIntoDatabase(movies: List<Movie>) {
//        withContext(Dispatchers.IO) {
//            currencyDao.insertCurrencies(movies.map { DbCurrency.fromDomainObject(it) })
//        }
//    }
}