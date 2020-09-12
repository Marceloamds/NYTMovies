package com.nyt.movies.presentation.view.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.MoviesList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.util.resource.Strings
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.view.movies.MovieFilterType

class ListMoviesViewModel constructor(
    private val getMoviesList: GetMoviesList,
    private val strings: Strings
) : BaseViewModel() {

    val moviesList: LiveData<MoviesList> get() = _moviesList

    private val _moviesList by lazy { MutableLiveData<MoviesList>() }

    var queryFilterType: MovieFilterType = MovieFilterType.FilterByName
    private var fullMoviesList: MoviesList? = null

    private var currentPage: Int = 0

    init {
        requestNewMovies(true)
    }

    fun onQueryChanged(query: String) {

    }

    fun filterFullList(movieFilterType: MovieFilterType) {

    }

    fun onProgressItemShown() {
        currentPage += 1
        requestNewMovies(false)
    }

    private fun requestNewMovies(showPlaceholder: Boolean) {
        launchDataLoad(showPlaceholder, onFailure = ::onFailure) {
            val moviesList = getMoviesList.execute(currentPage)
            if (moviesList?.status != "OK") {
                showCurrencyListErrorDialog()
            } else {
                fullMoviesList = moviesList
                _moviesList.value = moviesList
            }
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