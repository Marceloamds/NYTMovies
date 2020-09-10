package com.nyt.movies.data.remote.entity

import com.nyt.movies.domain.entity.currency.Currency
import com.nyt.movies.domain.entity.currency.CurrencyList
import com.google.gson.annotations.SerializedName

data class ApiCurrencyList(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("currencies") val currencies: HashMap<String, String>
) {

    fun toDomainObject(): CurrencyList {
        return CurrencyList(
            success = success,
            currencies = currencies
                .map { Currency(code = it.key, name = it.value) }
                .sortedBy { it.name }
        )
    }
}