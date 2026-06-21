package com.example.movieapi

data class MovieResponse(
    val Search : List<Movie>? = null,
    val Response : String ?,
    val Error : String ?
)