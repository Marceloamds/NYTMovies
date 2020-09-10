package com.nyt.movies.presentation.view.movies

sealed class MovieFilterType {
    object FilterByName : MovieFilterType()
    object FilterByCode : MovieFilterType()
}