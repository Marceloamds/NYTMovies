package com.nyt.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nyt.movies.BuildConfig
import com.nyt.movies.data.local.dao.CurrencyDao
import com.nyt.movies.data.local.dao.QuoteDao
import com.nyt.movies.data.local.entity.DbCurrency
import com.nyt.movies.data.local.entity.DbQuote

@Database(entities = [DbCurrency::class, DbQuote::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun quoteDao(): QuoteDao

    companion object {
        fun build(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context,
                MovieDatabase::class.java,
                BuildConfig.DATABASE_NAME
            ).build()
        }
    }
}