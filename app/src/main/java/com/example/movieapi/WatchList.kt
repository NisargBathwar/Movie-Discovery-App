package com.example.movieapi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import okhttp3.internal.wait

@Composable
fun WatchList(vm : MovieViewModel = hiltViewModel() , onClick: (String) -> Unit ) {

    val watchlist by vm.watchList.collectAsState()

    Column(Modifier
        .fillMaxSize()
        .padding(12.dp)) {

        if (watchlist.watchListMovie.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(70.dp)
                    )
                    Text(
                        text = "Your Watchlist is Empty",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD0D0D0)
                    )
                    Text(
                        text = "Save movies to watch later",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(watchlist.watchListMovie) { movie ->
                    WatchListItem(
                        movie,
                        onDelete = { movie ->
                            vm.removeFromWatchList(movie)
                        } ,
                        onClick = { id->
                            onClick(id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WatchListItem(
    movie: WatchListData,
    onDelete: (WatchListData) -> Unit ,
    onClick : (String)-> Unit
) {

    ElevatedCard(
        modifier = Modifier.width(160.dp).clickable{onClick(movie.imdbId)},
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {

        Column {
            Box {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                )
                IconButton(
                    onClick = {
                        onDelete(movie)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Text(
                text = movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD0D0D0),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}