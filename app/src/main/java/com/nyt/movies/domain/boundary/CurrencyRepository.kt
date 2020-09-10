package com.nyt.movies.domain.boundary

import com.nyt.movies.domain.entity.currency.CurrencyList

interface CurrencyRepository {

    suspend fun getCurrencyList(): CurrencyList?
}