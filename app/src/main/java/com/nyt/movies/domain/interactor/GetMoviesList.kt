package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.boundary.CurrencyRepository

class GetMoviesList constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun execute() = currencyRepository.getMoviesList()
}