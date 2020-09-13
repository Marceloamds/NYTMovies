package com.nyt.movies.presentation

import android.app.Application
import com.nyt.movies.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NYTMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NYTMoviesApplication)
            modules(
                listOf(
                    networkingModule(),
                    viewModelModule(),
                    databaseModule(),
                    repositoryModule(),
                    interactorModule(),
                    resourceModule()
                )
            )
        }
    }
}