package com.nyt.movies.data.remote.entity

import com.google.gson.annotations.SerializedName
import com.nyt.movies.domain.entity.movie.Multimedia

data class ApiMultimedia(
    @SerializedName("type") val type: String?,
    @SerializedName("src") val src: String?,
    @SerializedName("width") val width: Double?,
    @SerializedName("height") val height: Double?
) {

    fun toDomainObject() = Multimedia(
        type = type,
        src = src,
        width = width,
        height = height
    )
}