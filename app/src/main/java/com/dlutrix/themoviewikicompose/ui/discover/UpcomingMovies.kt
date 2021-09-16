package com.dlutrix.themoviewikicompose.ui.discover

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dlutrix.themoviewikicompose.R
import com.dlutrix.themoviewikicompose.data.entity.Movie
import com.dlutrix.themoviewikicompose.ui.theme.WhiteSmoke
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun UpcomingMovies(
    movies: LazyPagingItems<Movie>,
    isExpanded: Boolean,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    refreshDiscoverMovies: () -> Unit,
    navController: NavController,
    contentPadding: PaddingValues
) {

    var paddingState by remember {
        mutableStateOf(32.dp)
    }

    val paddingSize by animateDpAsState(
        targetValue = paddingState,
        spring(Spring.DampingRatioHighBouncy)
    )

    val scope = rememberCoroutineScope()

    SideEffect {
        paddingState = if (isExpanded) {
            contentPadding.calculateTopPadding() + 64.dp
        } else {
            32.dp
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = paddingSize, bottom = 16.dp)
        ) {
            items(movies) { movie ->
                UpcomingMoviesItem(movie = movie, navController = navController)
            }

            movies.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        scope.launch {
                            bottomSheetScaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        }
                        item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = movies.loadState.refresh as LoadState.Error
                        scope.launch {
                            val snackBarResult = bottomSheetScaffoldState.snackbarHostState
                                .showSnackbar(
                                    e.error.localizedMessage!!,
                                    actionLabel = "Retry",
                                    duration = SnackbarDuration.Indefinite
                                )
                            when (snackBarResult) {
                                SnackbarResult.Dismissed -> {
                                }
                                SnackbarResult.ActionPerformed -> {
                                    movies.refresh()
                                    refreshDiscoverMovies()
                                    bottomSheetScaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                }
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = movies.loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.localizedMessage!!,
                                onClickRetry = {
                                    movies.retry()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun UpcomingMoviesItem(
    movie: Movie?,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                end = 16.dp,
                start = 16.dp
            )
            .height(200.dp)
            .clickable {
                navController.navigate(
                    "movie_detail_screen/${movie?.id}/${movie?.title}"
                )
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Card(
                modifier = Modifier.height(180.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(top = 8.dp, end = 16.dp, start = 16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxSize()
                            .weight(2f),
                    ) {
                        Text(
                            text = movie?.title ?: "",
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(Font(R.font.nunito_bold)),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 36.dp)
                        )
                        Text(
                            text = movie?.overview ?: "",
                            fontWeight = FontWeight.W400,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topEnd = 16.dp,
                                    bottomStart = 16.dp
                                )
                            )
                            .fillMaxWidth(0.13f)
                            .background(MaterialTheme.colors.secondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie?.voteAverage.toString(),
                            modifier = Modifier.padding(8.dp),
                            color = WhiteSmoke,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W600
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth(0.33f),
            contentAlignment = Alignment.TopStart
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        "https://image.tmdb.org/t/p/w500/${movie?.posterPath}",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "${movie?.title} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Card(
        elevation = 8.dp,
        modifier = modifier.padding(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 64.dp
        )
    ) {
        Row(
            modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                maxLines = 1,
                modifier = Modifier.weight(1f),
            )
            OutlinedButton(onClick = onClickRetry) {
                Text(text = "Try again")
            }
        }
    }
}