package com.nyt.movies.presentation.view.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie

object OnMovieChangedObservable{

    val updatedMovie: LiveData<Movie> get() = _updatedMovie
    private val _updatedMovie by lazy { MutableLiveData<Movie>() }

    fun updateMovie(movie: Movie){
        _updatedMovie.value = movie
    }
}