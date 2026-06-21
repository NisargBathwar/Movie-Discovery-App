package com.example.movieapi

data class MovieState (
    val search : String = "",
    val searched : List<Movie> = emptyList(),
    val movie : List<Movie> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null
)