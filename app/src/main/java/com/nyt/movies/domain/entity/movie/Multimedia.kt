package com.nyt.movies.domain.entity.movie

import java.io.Serializable

data class Multimedia(
    val type: String?,
    val src: String?,
    val width: Double?,
    val height: Double?
) : Serializable