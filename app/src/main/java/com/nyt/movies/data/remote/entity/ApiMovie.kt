package com.nyt.movies.data.remote.entity

import com.google.gson.annotations.SerializedName
import com.nyt.movies.domain.entity.movie.Movie
import java.util.*

data class ApiMovie(
    @SerializedName("display_title") val displayTitle: String,
    @SerializedName("mpaa_rating") val mpaaRating: String,
    @SerializedName("critics_pick") val criticsPick: Int,
    @SerializedName("byline") val byline: String,
    @SerializedName("headline") val headline: String,
    @SerializedName("summary_short") val summaryShort: String,
    @SerializedName("publication_date") val publicationDate: Date,
    @SerializedName("opening_date") val openingDate: Date?,
    @SerializedName("date_updated") val dateUpdated: Date,
    @SerializedName("link") val link: ApiLink,
    @SerializedName("multimedia") val multimedia: ApiMultimedia?
) {

    fun toDomainObject() = Movie(
        displayTitle = displayTitle,
        byline = byline,
        headline = headline,
        summaryShort = summaryShort,
        publicationDate = publicationDate,
        openingDate = openingDate,
        link = link.toDomainObject(),
        multimedia = multimedia?.toDomainObject()
    )
}