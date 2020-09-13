package com.nyt.movies.presentation.di

import com.nyt.movies.presentation.view.movies.details.MovieDetailsViewModel
import com.nyt.movies.presentation.view.movies.list.ListMoviesViewModel
import com.nyt.movies.presentation.view.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {

    viewModel {
        MovieDetailsViewModel()
    }

    viewModel {
        ListMoviesViewModel(get(), get(), get())
    }

    viewModel { SplashViewModel() }
}