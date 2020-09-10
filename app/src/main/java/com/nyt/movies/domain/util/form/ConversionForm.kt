package com.nyt.movies.domain.util.form

import com.nyt.movies.domain.entity.currency.Currency

class ConversionForm() {

    var originCurrency: Currency? = null
    var destinationCurrency: Currency? = null
    var conversionValue: Double? = null

    fun isCurrenciesEmpty(): Boolean {
        return (originCurrency == null || destinationCurrency == null)
    }

    fun isValueEmpty(): Boolean {
        return (conversionValue == null)
    }
}