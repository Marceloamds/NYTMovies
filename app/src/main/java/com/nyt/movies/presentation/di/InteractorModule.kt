package com.nyt.movies.presentation.di

import com.nyt.movies.domain.interactor.GetMoviesList
import org.koin.dsl.module

fun interactorModule() = module {
    single {
        GetMoviesList(get())
    }
}