package com.nyt.movies.presentation.view.movies.details

import android.content.Context
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.presentation.util.navigation.NavData

class MovieDetailsNavData(
    private val movie: Movie
) : NavData {

    override fun navigate(context: Context) {
        context.startActivity(MovieDetailsActivity.createIntent(context, movie))
    }
}