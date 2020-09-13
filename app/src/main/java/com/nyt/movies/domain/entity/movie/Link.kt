package com.nyt.movies.domain.entity.movie

import java.io.Serializable

data class Link(
    val type: String?,
    val url: String,
    val suggestedLinkText: String?
) : Serializable