package com.example.movieapi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue

@Composable
fun MovieUi(vm : MovieViewModel = hiltViewModel() , onClick : (String)-> Unit) {

    val  state by vm.uiState.collectAsState()
    val listState = rememberSaveable(
        saver = LazyListState.Saver
    ) {
        LazyListState()
    }

    val marvelState = rememberSaveable(
        saver = LazyListState.Saver
    ) {
        LazyListState()
    }
    val homeListState = rememberSaveable(
        saver = LazyListState.Saver
    ) {
        LazyListState()
    }

    Column(
        Modifier
            .fillMaxWidth()
            .background(Color(0xFF121212))
            .padding(4.dp) ,
    ) {

        var index by remember { mutableStateOf(0) }
        val featuredMovies = remember(state.movie) {

            state.movie
                .distinctBy { it.imdbID }
                .shuffled()
                .take(10)
        }
        val animeMovies =
            state.movie.filter {
                it.Title?.contains("Naruto", true) == true
                        ||
                        it.Title?.contains("Jujutsu kaisen", true) == true
                        ||
                        it.Title?.contains("Attack", true) == true
            }
        val marvel =
            state.movie.filter {
                it.Title?.contains("Marvel", true) == true
                        ||
                        it.Title?.contains("Avengers", true) == true
                        ||
                        it.Title?.contains("Assembled", true) == true
            }




        val pageState = rememberPagerState(
            initialPage = Int.MAX_VALUE/2,
            pageCount = {Int.MAX_VALUE}
        )
        val haptic = LocalHapticFeedback.current
        val isDragged by pageState.interactionSource.collectIsDraggedAsState()


        Spacer(Modifier.height(20.dp))

        when{

            state.error != null ->{
                Text(text = state.error!!)
            }


            else->{
                LazyColumn(
                    state = homeListState ,
                    modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF121212)) ,
                    verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        if (featuredMovies.isNotEmpty()){
                            LaunchedEffect(Unit) {
                                while (isActive){
                                    delay(3000)

                                    if (featuredMovies.isNotEmpty() && !pageState.isScrollInProgress){
                                        val nextPage = (pageState.currentPage + 1) % state.movie.size
                                        pageState.animateScrollToPage(nextPage)
                                    }
                                }
                            }

                            LaunchedEffect(isDragged) {
                                if (isDragged){
                                    haptic.performHapticFeedback(
                                        HapticFeedbackType.LongPress
                                    )
                                }
                            }

                        }

                        if (state.movie.isEmpty()){
                           Box(Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                               Text("No Movie Yet , Search")
                           }
                        }
                        if (featuredMovies.isNotEmpty()){
                        HorizontalPager(state = pageState , contentPadding = PaddingValues(horizontal = 32.dp) , pageSpacing = (-30).dp) {page->
                            val movie = featuredMovies[ page % featuredMovies.size]

                            ElevatedCard(Modifier
                                .width(280.dp)
                                .height(450.dp)
                                .clickable{onClick(movie.imdbID)}
                                .graphicsLayer {
                                    val pageOffset = ((pageState.currentPage - page) + pageState.currentPageOffsetFraction).absoluteValue

                                    scaleY = lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                    scaleX = lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                    translationX = pageOffset * 1200f

                                }, shape = RoundedCornerShape(24.dp)) {

                                Box{
                                    AsyncImage(
                                        model = movie.Poster ,
                                        contentDescription = null ,
                                        contentScale = ContentScale.Crop ,
                                        modifier  = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                            }
                    }

                    item {
                        Text(
                            text = "Anime" ,
                            fontWeight = FontWeight.Bold ,
                            fontSize = 25.sp ,
                            modifier =  Modifier.padding(7.dp) ,
                            color = Color.LightGray
                        )
                    }


                    item {
                        LazyRow(
                            state = listState,
                            horizontalArrangement = Arrangement.spacedBy(12.dp) ,
                            modifier = Modifier.padding(5.dp)
                        ) {
                            items(animeMovies) { movie ->
                                MovieItem(
                                    movie,
                                    onClick = { onClick(movie.imdbID) }
                                )
                            }

                            if (state.isLoading && state.movie.isNotEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }

                        LaunchedEffect(listState) {
                            snapshotFlow {
                                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                            }
                                .collect { index ->
                                    if (index == state.movie.lastIndex && !state.isLoading) {
                                        vm.getMovies()
                                    }
                                }
                        }
                    }

                    item {
                        Text(
                            text = "Marvel" ,
                            fontWeight = FontWeight.Bold ,
                            fontSize = 25.sp ,
                            modifier =  Modifier.padding(7.dp) ,
                            color = Color.LightGray
                        )
                    }


                    item {
                        LazyRow(
                            state = marvelState,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(5.dp)
                        ) {
                            items(marvel) { movie ->
                                MovieItem(
                                    movie,
                                    onClick = { onClick(movie.imdbID) }
                                )
                            }

                            if (state.isLoading && state.movie.isNotEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie , onClick : ()-> Unit){
    ElevatedCard(
        Modifier
            .width(140.dp)
            .clickable { onClick() } ,
        shape = RoundedCornerShape(16.dp) ,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(Modifier.width(140.dp)) {

            AsyncImage(
                model = movie.Poster ?: "",
                contentDescription = movie.Title ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .width(140.dp)
                    .height(210.dp)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(12.dp))
            )

           Column(Modifier.padding(12.dp)) {

               Text(
                   text = movie.Title ?: "" ,
                   fontWeight = FontWeight.Bold ,
                   fontSize = 18.sp ,
                   maxLines = 1,
                   overflow = TextOverflow.Ellipsis
               )

               Spacer(Modifier.height(6.dp))

               Text(
                   text = movie.Year ?: "Unknown" ,
                   color = Color.Gray ,
                   fontSize = 14.sp
               )

               Spacer(Modifier.height(4.dp))

               Text(
                   text = movie.Type ?: "" ,
                   color = Color.LightGray ,
                   fontSize = 12.sp
               )
           }
        }
    }
}



@Composable
fun DetailScreen( vm: MovieViewModel = hiltViewModel(), id : String , onBack : ()-> Unit) {

    val dstate by vm.detailState.collectAsState()
    val state by vm.uiState.collectAsState()
    val ws by vm.watchList.collectAsState()

    val movie = dstate.movie
    val statemovie = state.movie.find { it.imdbID == id } ?: state.searched.find { it.imdbID == id }





    LaunchedEffect(id) {
        vm.getDetail(id)
    }

    Box(Modifier.fillMaxSize().background(Color(0xFF111111))){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
        ) {
            AsyncImage(
                model = movie?.Poster ?: "",
                contentDescription = movie?.Title ?: "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
//                    .clip(
//                        RoundedCornerShape(
//                            bottomEnd = 40.dp,
//                            bottomStart = 40.dp
//                        )
//                    )
            )
            Box(
                Modifier
                    .fillMaxSize()
//                    .clip(
//                        RoundedCornerShape(
//                            bottomStart = 40.dp,
//                            bottomEnd = 40.dp
//                        )
//                    )
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            )
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 445.dp)
        ) {
            item {
                Column(
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(
                        topEnd = 40.dp ,
                        topStart = 40.dp
                    )).background(Color(0xFF111111)).padding(14.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = movie?.Title ?: "",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp ,
                        color = Color(0xFFF5F5F5)

                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = ("IMDb   " + movie?.imdbRating),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp ,
                        color = Color(0xFFD0D0D0)
                    )
                    Column(
                        Modifier.fillMaxWidth().background(Color(0xFF111111)),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(
                                text = movie?.Year ?: "",
                                fontWeight = FontWeight.Bold  ,
                                color = Color(0xFFD0D0D0)
                            )
                            Text("•" , color = Color.Gray)
                            Text(
                                text = movie?.Rated ?: "",
                                fontWeight = FontWeight.SemiBold ,
                                color =Color(0xFFD0D0D0)
                            )
                            Text("•" , color = Color.Gray)
                            Text(
                                text = movie?.Runtime ?: "",
                                fontWeight = FontWeight.SemiBold ,
                                color = Color(0xFFD0D0D0)
                            )
                        }

                        ElevatedButton(
                            onClick = {},
                            modifier = Modifier.width(350.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Watch Now"
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Watch Now" ,
                                color = Color(0xFFD0D0D0)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = movie?.Genre?.replace(",", "|") ?: "",
                                color = Color(0xFFD0D0D0),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.W900
                            )
                            Text(
                                text = movie?.Plot ?: "null",
                                color = Color(0xFFB3B3B3),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.W100
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                                val context = LocalContext.current
                                ElevatedButton(onClick = {
                                    movie?.let {
                                        vm.addToWatchList(
                                            movie = WatchListData(
                                                imdbId = movie.imdbId ?: "",
                                                title = movie.Title ?: "",
                                                poster = movie.Poster ?: ""
                                            )
                                        )
                                    }
                                    Toast.makeText(context , "Added to Watchlist" , Toast.LENGTH_LONG).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Watchlist"
                                    )
                                    Spacer(Modifier.width(3.dp))
                                    Text("Watchlist")
                                }
                                ElevatedButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share"
                                    )
                                    Spacer(Modifier.width(3.dp))
                                    Text("Share")
                                }
                                var isLiked by remember {
                                    mutableStateOf(false)
                                }
                                ElevatedButton(onClick = {
                                    isLiked = !isLiked
                                }) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Like"
                                    )
                                    Spacer(Modifier.width(3.dp))
                                    Text("Like")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
