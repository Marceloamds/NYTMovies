package com.nyt.movies.presentation.di

import com.nyt.movies.domain.util.resource.Strings
import com.nyt.movies.presentation.util.error.ErrorHandler
import com.nyt.movies.presentation.util.logger.Logger
import org.koin.dsl.module

fun resourceModule() = module {

    single {
        Strings(get())
    }

    single {
        Logger(get())
    }

    single {
        ErrorHandler(get(), get())
    }
}
