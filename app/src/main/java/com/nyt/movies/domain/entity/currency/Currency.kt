package com.nyt.movies.domain.entity.currency

import android.content.Context
import com.nyt.movies.R
import java.io.Serializable

data class Currency(
    val code: String,
    val name: String
) : Serializable {

    fun getFormattedString(context: Context) =
        context.getString(R.string.currency_template, name, code)
}