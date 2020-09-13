package com.nyt.movies.presentation.view.movies.list

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.R
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.MoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.util.extension.adding
import com.nyt.movies.presentation.view.movies.details.MovieDetailsNavData

class ListMoviesViewModel constructor(
    private val getMoviesList: GetMoviesList,
    private val context: Context
) : BaseViewModel() {

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    val progressVisible: LiveData<Boolean> get() = _progressVisible
    val shareMovie: LiveData<Movie> get() = _shareMovie

    private val _moviesList by lazy { MutableLiveData<List<Movie>>() }
    private val _progressVisible by lazy { MutableLiveData<Boolean>() }
    private val _shareMovie by lazy { MutableLiveData<Movie>() }

    private var fullMoviesList: List<Movie>? = listOf()

    private var currentPage: Int = 0
    private var currentQuery = ""

    init {
        requestNewMovies()
    }

    fun onQueryChanged(query: String) {
        currentQuery = query
        currentPage = 0
        fullMoviesList = listOf()
        requestNewMovies()
    }

    fun onMovieSelected(movie: Movie) {
        goTo(MovieDetailsNavData(movie))
    }

    fun onLikeClicked(movie: Movie) {
        // TODO -> Like movie
    }

    fun onShareClicked(movie: Movie) {
        _shareMovie.value = movie
    }

    fun onProgressItemShown() {
        currentPage += 1
        requestNewMovies(false)
    }

    private fun requestNewMovies(showPlaceholder: Boolean = true) {
        launchDataLoad(showPlaceholder, onFailure = ::onFailure) {
            val moviesList = getMoviesList.execute(currentPage, currentQuery)
            fullMoviesList = fullMoviesList?.adding(moviesList?.movies)
            setMoviesList(moviesList)
        }
    }

    private fun setMoviesList(moviesList: MoviesList?) {
        if (moviesList?.status != "OK") {
            showCurrencyListErrorDialog()
        } else {
            _progressVisible.value = moviesList.hasMore
            _moviesList.value = fullMoviesList
        }
    }

    private fun showCurrencyListErrorDialog() {
        setDialog(
            DialogData.confirm(
                context.getString(R.string.error_title),
                context.getString(R.string.movies_list_error),
                { /* Do Nothing */ },
                context.getString(R.string.global_ok),
                true
            )
        )
    }

    private fun onFailure(throwable: Throwable) {
        setDialog(throwable) { requestNewMovies(true) }
    }
}