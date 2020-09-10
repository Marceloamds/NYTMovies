package com.nyt.movies.presentation.view.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nyt.movies.domain.entity.currency.Currency
import com.nyt.movies.domain.entity.currency.CurrencyList
import com.nyt.movies.domain.interactor.GetMoviesList
import com.nyt.movies.domain.util.resource.Strings
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.dialog.DialogData
import com.nyt.movies.presentation.view.movies.MovieFilterType

class ListMoviesViewModel constructor(
    private val getCurrencyList: GetMoviesList,
    private val strings: Strings
) : BaseViewModel() {

    val currencyList: LiveData<List<Currency>> get() = _currencyList
    private val _currencyList by lazy { MutableLiveData<List<Currency>>() }

    var queryFilterType: MovieFilterType = MovieFilterType.FilterByName
    private var fullCurrencyList: CurrencyList? = null

    init {
        getCurrencyList()
    }

    fun onQueryChanged(query: String) {
        _currencyList.value = fullCurrencyList?.currencies?.filter {
            when (queryFilterType) {
                is MovieFilterType.FilterByName -> it.name.contains(query, true)
                is MovieFilterType.FilterByCode -> it.code.contains(query, true)
            }
        }
    }

    fun filterFullList(currencyFilterType: MovieFilterType) {
        _currencyList.value = fullCurrencyList?.currencies?.sortedBy {
            when (currencyFilterType) {
                is MovieFilterType.FilterByName -> it.name
                is MovieFilterType.FilterByCode -> it.code
            }
        }
    }

    private fun getCurrencyList() {
        launchDataLoad(onFailure = ::onFailure) {
            val currencyList = getCurrencyList.execute()
            if (currencyList?.success == false) {
                showCurrencyListErrorDialog()
            } else {
                fullCurrencyList = currencyList
                _currencyList.value = fullCurrencyList?.currencies
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
        setDialog(throwable, ::getCurrencyList)
    }
}