package com.nyt.movies.data.local.dao

import androidx.room.*
import com.nyt.movies.data.local.entity.DbMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<DbMovie>)

    @Update
    suspend fun updateMovie(movie: DbMovie)

    @Query("SELECT * FROM movie WHERE display_title LIKE :query OR summary_short LIKE :query ORDER BY publication_date DESC LIMIT :limit")
    suspend fun getMovies(limit: Int, query: String): List<DbMovie>

    @Query("SELECT * FROM movie WHERE is_favorite = 1 ORDER BY publication_date DESC LIMIT :limit")
    suspend fun getFavoriteMovies(limit: Int): List<DbMovie>

    @Query("SELECT COUNT(*) FROM movie WHERE is_favorite = 1")
    suspend fun getFavoriteMoviesCount(): Int

    @Query("SELECT COUNT(*) FROM movie WHERE display_title LIKE :query OR summary_short LIKE :query")
    suspend fun getMoviesCount(query: String): Int
}