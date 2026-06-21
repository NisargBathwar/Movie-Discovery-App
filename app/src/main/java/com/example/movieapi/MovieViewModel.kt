package com.example.movieapi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MovieDetailState(
    val movie : MovieDetailDTO? = null,
    val seached : MovieDetailDTO? = null ,
    val isLoading : Boolean = false  ,
    val error : String  = ""
)


data class WatchListState(
    val watchListMovie : List<WatchListData> = emptyList() ,
    val isLoading : Boolean = false ,
    val error : String? = null
)


@HiltViewModel
class MovieViewModel @Inject constructor(private val getMoviesUseCases: GetMoviesUseCases) : ViewModel() {

    private var currentPage = 1

    private val _uiState = MutableStateFlow(MovieState())
    val uiState : StateFlow<MovieState> = _uiState

    private val _detailState = MutableStateFlow(MovieDetailState())
    val detailState : StateFlow<MovieDetailState> = _detailState

    private val _watchList = MutableStateFlow(WatchListState())
    val watchList : StateFlow<WatchListState> = _watchList

    init {

       preloadMovie()
        observeMovies()
        observeWatchList()
    }


    private fun preloadMovie(){
        viewModelScope.launch {
            getMoviesUseCases.clearMovieFromDB()
            listOf(
                "Naruto",
                "Marvel",
                "Batman",
                "Avengers"
            ).forEach { movie ->

                getMoviesUseCases.preloadedContent(movie)
            }
        }
    }


    private fun observeMovies(){
        viewModelScope.launch {
            getMoviesUseCases.getMovieFromDB().collect { value ->
                _uiState.update {
                    it.copy(movie = value )
                }
            }
        }
    }


    private fun observeWatchList(){
        viewModelScope.launch {
            getMoviesUseCases.getWatchList().collect { value ->
                _watchList.update {
                    it.copy(
                        watchListMovie = value
                    )
                }
            }
        }
    }




    fun addToWatchList(movie : WatchListData){
        viewModelScope.launch {
            getMoviesUseCases.addToWatchList(movie)
        }
        Log.d(
            "WATCHLIST_ID",
            movie.imdbId ?: "NULL"
        )
    }


    fun removeFromWatchList(movie: WatchListData){
        viewModelScope.launch {
            getMoviesUseCases.removeFromWatchList(movie)
        }
    }


    fun changeSearch(new : String){
        _uiState.update {
            it.copy(
                search = new
            )
        }
    }

    fun getDetail(imdbId : String){
        viewModelScope.launch {
            _detailState.update {
                it.copy(
                    isLoading = true
                )
            }

            try {
                val response = getMoviesUseCases.getDetail(imdbId)
                _detailState.update {
                    it.copy(
                        movie = response ,
                        seached = response,
                        isLoading = false
                    )
                }
            }catch (e : Exception){
                _detailState.update {
                    it.copy(
                        isLoading = false ,
                        error = e.message ?: ""
                    )
                }
            }
        }
    }

    fun searchMovies(){
        currentPage = 1
        getMovies()
        _uiState.update {
            it.copy(
                search = ""
            )
        }
    }


     fun getMovies(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            try {

                val response = getMoviesUseCases(
                    search = _uiState.value.search ,
                    apikey = "a2a6c564" ,
                    page = currentPage
                )

                Log.d("response" , response.toString())

                _uiState.update {
                    it.copy(
                        searched = response.Search ?: emptyList(),
                        isLoading = false
                    )
                }

                currentPage++

            }catch (e : Exception){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}