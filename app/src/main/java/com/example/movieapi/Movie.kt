package com.example.movieapi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movie")
data class Movie(

    @PrimaryKey
    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Title")
    val Title: String? = null,

    @SerializedName("Year")
    val Year: String? = null,

    @SerializedName("Poster")
    val Poster: String? = null ,

    @SerializedName("Type")
    val Type : String? = null,

)