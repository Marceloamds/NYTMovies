package com.nyt.movies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nyt.movies.domain.entity.movie.Movie

@Entity(tableName = "currency")
data class DbMovie(
    @PrimaryKey @ColumnInfo(name = "code")
    var code: String,
    @ColumnInfo(name = "name") var name: String
) {

}