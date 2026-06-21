package com.example.movieapi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun SearchScreen(vm : MovieViewModel  = hiltViewModel() , onClick: (String) -> Unit) {

    val state by vm.uiState.collectAsState()
    Column(Modifier.fillMaxSize().padding(6.dp)) {
        OutlinedTextField(
            value = state.search,
            onValueChange = {
                vm.changeSearch(it)
            },
            label = {
                Text("Enter the movie name")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    vm.searchMovies()
                }
            ) ,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

//        Spacer(Modifier.height(15.dp))

        if (state.searched.isEmpty()){
            Column(Modifier.fillMaxSize() ,
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(Modifier.fillMaxWidth() , contentAlignment = Alignment.Center){
                    Text("Search Movies Or Series")
                }
            }
        }else{
            LazyVerticalGrid(
                columns = GridCells.Fixed(2) ,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(state.searched){ movie->
                    SearchedItem(
                        movie,
                        onClick = { onClick(movie.imdbID) }
                    )
                }
            }
        }
    }
}


@Composable
fun SearchedItem(movie: Movie , onClick : ()-> Unit){

    ElevatedCard(Modifier.fillMaxSize().clickable{onClick()} ,
        shape = RoundedCornerShape(16.dp) ,
        elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.Poster ?: "" ,
                contentDescription = null  ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier.fillMaxWidth().aspectRatio(2f/3f).clip(
                    RoundedCornerShape(12.dp)
                )
            )
            Spacer(Modifier.height(7.dp))
           Column(Modifier.padding(12.dp)) {
               Text(
                   text = movie.Title ?: ""  ,
                   overflow = TextOverflow.Ellipsis ,
                   maxLines = 1,
                   fontSize = 18.sp ,
                   fontWeight = FontWeight.Bold
               )
               Spacer(Modifier.height(7.dp))
               Text(
                   text = movie.Year ?: "",
                   fontSize = 14.sp ,
                   color = Color.Gray,
                   fontWeight = FontWeight.SemiBold
               )
               Spacer(Modifier.height(7.dp))
               Text(
                   text = movie.Type ?: "",
                   fontSize = 12.sp ,
                   color = Color.LightGray,
                   fontWeight = FontWeight.W900
               )
           }
        }
    }
}