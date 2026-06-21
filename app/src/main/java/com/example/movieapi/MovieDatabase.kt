package com.example.movieapi

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([Movie::class , WatchListData::class] , version = 3 , exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao() : MovieDao
}