package com.nyt.movies.presentation.di

import com.nyt.movies.data.local.MovieDatabase
import org.koin.dsl.module

fun databaseModule() = module {
    single { MovieDatabase.build(get()) }

    single {
        val movieDatabase = get() as MovieDatabase
        movieDatabase.movieDao()
    }
}