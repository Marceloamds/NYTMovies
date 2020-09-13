package com.nyt.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nyt.movies.BuildConfig
import com.nyt.movies.data.local.dao.MovieDao
import com.nyt.movies.data.local.entity.DbMovie

@Database(entities = [DbMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

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