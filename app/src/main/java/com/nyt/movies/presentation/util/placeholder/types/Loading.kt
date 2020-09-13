package com.nyt.movies.presentation.util.placeholder.types

import com.nyt.movies.presentation.util.placeholder.Placeholder

class Loading : Placeholder {
    override val progressVisible: Boolean get() = true
    override val visible: Boolean get() = true
}