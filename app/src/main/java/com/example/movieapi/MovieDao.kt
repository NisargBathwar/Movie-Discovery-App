package com.example.movieapi

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie ")
    fun getMovies(): Flow<List<Movie>>

    @Query("select * from watchlist")
    fun getWatchList() : Flow<List<WatchListData>>

    @Upsert
    suspend fun insertInWatchList(movie: WatchListData)


    @Delete
    suspend fun delete(movie : WatchListData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies : List<Movie>)

    @Query("delete from Movie")
    suspend fun clearMovie()

}