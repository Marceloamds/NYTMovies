package com.nyt.movies.presentation.view.splash

import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.constants.SPLASH_DELAY
import com.nyt.movies.presentation.view.movies.details.MovieDetailsNavData
import com.nyt.movies.presentation.view.movies.list.ListMoviesNavData
import kotlinx.coroutines.delay

class SplashViewModel : BaseViewModel() {

    init {
        launchDataLoad {
            delay(SPLASH_DELAY)
            goTo(ListMoviesNavData())
        }
    }
}