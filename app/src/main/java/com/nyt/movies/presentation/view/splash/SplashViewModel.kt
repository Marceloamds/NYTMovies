package com.nyt.movies.presentation.view.splash

import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.view.movies.list.ListMoviesNavData
import kotlinx.coroutines.delay

class SplashViewModel : BaseViewModel() {

    init {
        launchDataLoad {
            delay(SPLASH_DELAY)
            goTo(ListMoviesNavData())
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}