package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import com.nyt.movies.presentation.util.navigation.NavData

class ListMoviesNavData : NavData {

    override fun navigate(context: Context) {
        context.startActivity(ListMoviesActivity.createIntent(context))
    }
}