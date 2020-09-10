package com.nyt.movies.presentation.di

import com.nyt.movies.data.repository.DefaultCurrencyRepository
import com.nyt.movies.data.repository.DefaultQuoteRepository
import com.nyt.movies.domain.boundary.CurrencyRepository
import com.nyt.movies.domain.boundary.QuoteRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    single {
        DefaultCurrencyRepository(get(), get()) as CurrencyRepository
    }

    single {
        DefaultQuoteRepository(get(), get()) as QuoteRepository
    }
}