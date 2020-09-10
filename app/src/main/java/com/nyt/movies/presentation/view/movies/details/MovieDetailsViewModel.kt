package com.nyt.movies.presentation.view.movies.details

import com.nyt.movies.domain.util.resource.Strings
import com.nyt.movies.presentation.util.base.BaseViewModel
import com.nyt.movies.presentation.util.dialog.DialogData

class MovieDetailsViewModel constructor(
    private val strings: Strings
) : BaseViewModel() {

    private fun showEmptyCurrenciesDialog() {
        setDialog(
            DialogData.confirm(
                strings.emptyFieldsErrorTitle,
                strings.emptyCurrenciesError,
                { /* Do Nothing */ },
                strings.globalOk,
                true
            )
        )
    }
}