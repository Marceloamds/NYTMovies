package com.nyt.movies.presentation.di

import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.interactor.PerformConversion
import org.koin.dsl.module

fun interactorModule() = module {
    single {
        GetMoviesList(get())
    }

    single {
        PerformConversion()
    }
}