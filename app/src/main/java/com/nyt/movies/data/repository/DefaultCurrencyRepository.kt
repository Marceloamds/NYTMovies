package com.nyt.movies.data.repository

import com.nyt.movies.data.local.dao.CurrencyDao
import com.nyt.movies.data.local.entity.DbCurrency
import com.nyt.movies.data.remote.client.ApiClient
import com.nyt.movies.data.util.request.handleException
import com.nyt.movies.domain.boundary.CurrencyRepository
import com.nyt.movies.domain.entity.currency.Currency
import com.nyt.movies.domain.entity.currency.CurrencyList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultCurrencyRepository constructor(
    private val apiClient: ApiClient,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    override suspend fun getCurrencyList(): CurrencyList? {
        return handleException(::getFromDatabase) {
            apiClient.getCurrencyList()?.toDomainObject()
                ?.also { saveCurrenciesIntoDatabase(it.currencies) }
        }
    }

    private suspend fun getFromDatabase(e: Throwable): CurrencyList? {
        val currencyList = currencyDao.getCurrencies()
        if (currencyList.isNotEmpty()) {
            return CurrencyList(true, currencyList.map { it.toDomainObject() })
        } else {
            throw e
        }
    }

    private suspend fun saveCurrenciesIntoDatabase(currencies: List<Currency>) {
        withContext(Dispatchers.IO) {
            currencyDao.insertCurrencies(currencies.map { DbCurrency.fromDomainObject(it) })
        }
    }
}