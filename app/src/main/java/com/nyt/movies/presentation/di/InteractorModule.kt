package com.nyt.movies.presentation.di

import com.nyt.movies.domain.interactor.GetFavoriteMoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.interactor.LikeMovie
import org.koin.dsl.module

fun interactorModule() = module {
    single {
        GetMoviesList(get())
    }

    single {
        LikeMovie(get())
    }

    single {
        GetFavoriteMoviesList(get())
    }
}