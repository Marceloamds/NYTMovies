package com.nyt.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.movies.data.local.entity.DbMovie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(movies: List<DbMovie>)

    @Query("SELECT * FROM currency")
    suspend fun getCurrencies(): List<DbMovie>
}