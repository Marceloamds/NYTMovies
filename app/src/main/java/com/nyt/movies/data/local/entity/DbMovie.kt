package com.nyt.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nyt.movies.domain.entity.movie.Link
import com.nyt.movies.domain.entity.movie.Movie
import com.nyt.movies.domain.entity.movie.Multimedia
import java.util.*

@Entity(tableName = "movie")
data class DbMovie(
    @PrimaryKey
    @ColumnInfo(name = "link_url") val linkUrl: String,
    @ColumnInfo(name = "display_title") val displayTitle: String,
    @ColumnInfo(name = "critics_pick") val criticsPick: Int,
    @ColumnInfo(name = "byline") val byline: String,
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "summary_short") val summaryShort: String,
    @ColumnInfo(name = "publication_date") val publicationDate: Long,
    @ColumnInfo(name = "opening_date") val openingDate: Long?,
    @ColumnInfo(name = "link_type") val linkType: String?,
    @ColumnInfo(name = "suggested_link_text") val suggestedLinkText: String?,
    @ColumnInfo(name = "multimedia_type") val multimediaType: String?,
    @ColumnInfo(name = "multimedia_src") val multimediaSrc: String?,
    @ColumnInfo(name = "multimedia_width") val multimediaWidth: Double?,
    @ColumnInfo(name = "multimedia_height") val multimediaHeight: Double?,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean
) {

    fun toDomainObject() = Movie(
        displayTitle = displayTitle,
        criticsPick = criticsPick,
        byline = byline,
        headline = headline,
        summaryShort = summaryShort,
        publicationDate = Date(publicationDate),
        openingDate = openingDate?.let { Date(it) },
        link = Link(linkType, linkUrl, suggestedLinkText),
        multimedia = Multimedia(multimediaType, multimediaSrc, multimediaWidth, multimediaHeight),
        isFavorite = isFavorite
    )

    companion object {
        fun fromDomainObject(movie: Movie) = DbMovie(
            displayTitle = movie.displayTitle,
            criticsPick = movie.criticsPick,
            byline = movie.byline,
            headline = movie.headline,
            summaryShort = movie.summaryShort,
            publicationDate = movie.publicationDate.time,
            openingDate = movie.openingDate?.time,
            linkType = movie.link.type,
            linkUrl = movie.link.url,
            suggestedLinkText = movie.link.suggestedLinkText,
            multimediaType = movie.multimedia?.type,
            multimediaSrc = movie.multimedia?.src,
            multimediaWidth = movie.multimedia?.width,
            multimediaHeight = movie.multimedia?.height,
            isFavorite = movie.isFavorite
        )
    }
}