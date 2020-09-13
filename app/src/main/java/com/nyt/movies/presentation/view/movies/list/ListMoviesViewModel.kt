package com.nyt.movies.presentation.view.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.MoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.util.resource.Strings
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.util.extension.adding
import com.nyt.movies.presentation.view.movies.details.MovieDetailsNavData

class ListMoviesViewModel constructor(
    private val getMoviesList: GetMoviesList,
    private val strings: Strings
) : BaseViewModel() {

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    val progressVisible: LiveData<Boolean> get() = _progressVisible
    val resetList: LiveData<Boolean> get() = _resetList

    private val _moviesList by lazy { MutableLiveData<List<Movie>>() }
    private val _progressVisible by lazy { MutableLiveData<Boolean>() }
    private val _resetList by lazy { MutableLiveData<Boolean>() }

    private var fullMoviesList: List<Movie>? = listOf()

    private var currentPage: Int = 0
    private var currentQuery = ""

    init {
        requestNewMovies()
    }

    fun onQueryChanged(query: String) {
        currentQuery = query
        fullMoviesList = listOf()
        requestNewMovies()
    }

    fun onQueryClosed() {
        currentQuery = ""
        fullMoviesList = listOf()
        requestNewMovies()
    }

    fun onMovieSelected(movie: Movie) {
        goTo(MovieDetailsNavData(movie))
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
            _resetList.value = showPlaceholder
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
                strings.errorTitle,
                strings.currencyListError,
                { /* Do Nothing */ },
                strings.globalOk,
                true
            )
        )
    }

    private fun onFailure(throwable: Throwable) {
        setDialog(throwable) { requestNewMovies(true) }
    }
}