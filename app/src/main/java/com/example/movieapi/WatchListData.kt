package com.example.movieapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("WatchList")
data class WatchListData (
    @PrimaryKey
    val imdbId : String  ,
    val title : String ,
    val poster : String
)