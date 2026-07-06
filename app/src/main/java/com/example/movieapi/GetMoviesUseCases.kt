package com.example.movieapi

import javax.inject.Inject

class GetMoviesUseCases @Inject constructor(private val repo : MovieRepo) {
    suspend operator fun invoke(
        search : String ,
        page : Int ,
        apikey : String
    ) : MovieResponse{
        return repo.getMovies(
            search ,
            page = page ,
            apikey = ""
        )
    }

    fun getMovieFromDB() = repo.getMoviesFromDB()
    suspend fun clearMovieFromDB() = repo.clearMoviesFromDB()
    suspend fun preloadedContent(movie : String) = repo.preloaded(
        search = movie
    )

    fun getWatchList() = repo.getWatchList()
    suspend fun addToWatchList(moviesUseCases: WatchListData ) = repo.addToWatchList(moviesUseCases)
    suspend fun removeFromWatchList(moviesUseCases: WatchListData) = repo.removeFromWatchList(moviesUseCases)


    suspend fun getDetail(imdbId : String) = repo.getMoviesDetail(imdbId)
}