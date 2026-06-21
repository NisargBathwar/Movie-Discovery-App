package com.example.movieapi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNav(val route : String , val icon : ImageVector , val title : String) {
    object Home : BottomNav(
        "Home" ,
        Icons.Default.Home ,
        "Home"
    )

    object Search : BottomNav(
        "Search" ,
        Icons.Default.Search ,
        "Search"
    )

    object WatchList : BottomNav(
        "WatchList" ,
        Icons.Default.Favorite ,
        "WatchList"
    )
}