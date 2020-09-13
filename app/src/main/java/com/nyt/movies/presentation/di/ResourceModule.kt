package com.nyt.movies.presentation.di

import com.nyt.movies.presentation.util.error.ErrorHandler
import org.koin.dsl.module

fun resourceModule() = module {

    single {
        ErrorHandler(get())
    }
}
