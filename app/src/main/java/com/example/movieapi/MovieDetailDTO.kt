package com.example.movieapi

import com.google.gson.annotations.SerializedName

data class MovieDetailDTO(
    @SerializedName("imdbID")
    val imdbId : String,
    val Poster : String ,
    val Title : String,
    val Year : String,
    val Rated : String,
    val Runtime : String,
    val Genre : String,
    val Plot : String,
    val Language : String,
    val imdbRating : String,

    )