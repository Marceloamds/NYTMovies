package com.nyt.movies.presentation.view.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.interactor.LikeMovie
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.view.movies.OnMovieChangedObservable

class MovieDetailsViewModel(
    private val likeMovie: LikeMovie
) : BaseViewModel() {

    val shareMovie: LiveData<Movie> get() = _shareMovie
    private val _shareMovie by lazy { MutableLiveData<Movie>() }

    fun onLikeClicked(movie: Movie?) {
        movie?.let {
            launchDataLoad {
                OnMovieChangedObservable.updateMovie(likeMovie.execute(movie))
            }
        }
    }

    fun onShareClicked(movie: Movie?) {
        _shareMovie.value = movie
    }
}