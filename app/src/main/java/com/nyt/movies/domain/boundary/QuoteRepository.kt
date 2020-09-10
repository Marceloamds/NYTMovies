package com.nyt.movies.domain.boundary

import com.nyt.movies.domain.entity.quote.CurrentQuotes

interface QuoteRepository {

    suspend fun getCurrentQuotes(): CurrentQuotes?
}