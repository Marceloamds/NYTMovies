package com.nyt.movies.domain.interactor

import com.nyt.movies.domain.entity.quote.CurrentQuotes
import com.nyt.movies.domain.util.form.ConversionForm

class PerformConversion {

    fun execute(
        conversionForm: ConversionForm,
        currentQuotes: CurrentQuotes
    ): Double? {
        val originCurrencyQuote =
            currentQuotes.quotes.find { it.currencyCode == conversionForm.originCurrency?.code }
        val destinationCurrencyQuote =
            currentQuotes.quotes.find { it.currencyCode == conversionForm.destinationCurrency?.code }

        val totalConversionValue = nullableMultiplication(
            destinationCurrencyQuote?.convertedValue,
            conversionForm.conversionValue
        )

        return nullableDivision(totalConversionValue, originCurrencyQuote?.convertedValue)
    }

    private fun nullableDivision(firstDouble: Double?, secondDouble: Double?): Double? {
        return if (firstDouble == null || secondDouble == null) {
            null
        } else {
            firstDouble / secondDouble
        }
    }

    private fun nullableMultiplication(firstDouble: Double?, secondDouble: Double?): Double? {
        return if (firstDouble == null || secondDouble == null) {
            null
        } else {
            firstDouble * secondDouble
        }
    }
}