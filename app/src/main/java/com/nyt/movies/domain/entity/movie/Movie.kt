package com.nyt.movies.domain.entity.movie

import java.io.Serializable
import java.util.*

data class Movie(
    val displayTitle: String,
    val byline: String,
    val headline: String,
    val summaryShort: String,
    val publicationDate: Date,
    val openingDate: Date?,
    val link: Link,
    val multimedia: Multimedia?
) : Serializable
