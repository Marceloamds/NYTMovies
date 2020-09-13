package com.nyt.movies.presentation.di

import com.nyt.movies.presentation.util.error.ErrorHandler
import com.nyt.movies.presentation.util.logger.Logger
import org.koin.dsl.module

fun resourceModule() = module {

    single {
        Logger(get())
    }

    single {
        ErrorHandler(get(), get())
    }
}
