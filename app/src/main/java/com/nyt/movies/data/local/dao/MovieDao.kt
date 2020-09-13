package com.nyt.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.movies.data.local.entity.DbMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<DbMovie>)

    @Query("SELECT * FROM movie WHERE display_title LIKE :query OR summary_short LIKE :query ORDER BY publication_date DESC LIMIT :limit")
    suspend fun getMovies(limit: Int, query: String): List<DbMovie>

    @Query("SELECT COUNT(*) FROM movie WHERE display_title LIKE :query OR summary_short LIKE :query")
    suspend fun getMoviesCount(query: String): Int
}