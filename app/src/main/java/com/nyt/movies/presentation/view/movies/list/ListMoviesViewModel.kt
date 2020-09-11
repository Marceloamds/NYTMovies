package com.nyt.movies.presentation.view.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.movie.Movie
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

    val moviesList: LiveData<List<Movie>> get() = _moviesList
    private val _moviesList by lazy { MutableLiveData<List<Movie>>() }

    var queryFilterType: MovieFilterType = MovieFilterType.FilterByName
    private var fullMoviesList: MoviesList? = null

    init {
        getMoviesList()
    }

    fun onQueryChanged(query: String) {
        _moviesList.value = fullMoviesList?.movies?.filter {
            when (queryFilterType) {
                is MovieFilterType.FilterByName -> it.displayTitle.contains(query, true)
            }
        }
    }

    fun filterFullList(currencyFilterType: MovieFilterType) {
        _moviesList.value = fullMoviesList?.movies?.sortedBy {
            when (currencyFilterType) {
                is MovieFilterType.FilterByName -> it.displayTitle
            }
        }
    }

    private fun getMoviesList() {
        launchDataLoad(onFailure = ::onFailure) {
            val currencyList = getMoviesList.execute()
            if (currencyList?.status != "OK") {
                showCurrencyListErrorDialog()
            } else {
                fullMoviesList = currencyList
                _moviesList.value = fullMoviesList?.movies
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
        setDialog(throwable, ::getMoviesList)
    }
}