package com.nyt.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.movies.data.local.entity.DbCurrency

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<DbCurrency>)

    @Query("SELECT * FROM currency")
    suspend fun getCurrencies(): List<DbCurrency>
}