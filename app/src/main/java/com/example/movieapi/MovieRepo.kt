package com.example.movieapi

import android.util.Log
import javax.inject.Inject

class MovieRepo @Inject constructor(private val api: MovieApi , private val dao: MovieDao) {
    suspend fun getMovies(search : String , apikey : String , page : Int) : MovieResponse{
        val response = api.getMovies(
            Search = search.toLowerCase().trim() ,
            apikey = apikey ,
            page = page
        )

        dao.insert(
            movies = response.Search ?: emptyList()
        )

        return response
    }

    suspend fun preloaded(search : String){
        val response  = api.getMovies(
            search ,
            apikey ="your_api" ,
            page = 1

        )
        dao.insert(response.Search ?: emptyList())
        Log.d(
            "DB",
            dao.getMovies().toString()
        )
    }

    fun getMoviesFromDB() = dao.getMovies()
    suspend fun clearMoviesFromDB() = dao.clearMovie()

    suspend fun addToWatchList(movie : WatchListData) = dao.insertInWatchList(movie)
    fun getWatchList() = dao.getWatchList()
    suspend fun removeFromWatchList(movie: WatchListData) = dao.delete(movie)

    suspend fun getMoviesDetail(imdbId : String) :  MovieDetailDTO {
        return api.getDetails(
            imdbId
        )
    }
}