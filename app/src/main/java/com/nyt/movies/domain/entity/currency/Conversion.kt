package com.nyt.movies.domain.entity.currency

data class Conversion(
    val originCurrency: Currency?,
    val destinationCurrency: Currency?,
    val convertedValue: Double
)