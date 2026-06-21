package com.example.movieapi

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun getMovies(
        @Query("s")
        Search : String ,

        @Query("page")
        page : Int ,

        @Query("apikey")
        apikey : String

    ) : MovieResponse


    @GET("/")
    suspend fun getDetails(
        @Query("i")
        imdbId : String ,

        @Query("Plot")
        plot : String = "short" ,

        @Query("apikey")
        apikey : String  = "your_api_key"
    ) : MovieDetailDTO

}