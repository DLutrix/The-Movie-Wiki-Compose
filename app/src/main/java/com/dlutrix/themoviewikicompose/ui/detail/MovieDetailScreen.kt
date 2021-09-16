package com.dlutrix.themoviewikicompose.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.dlutrix.themoviewikicompose.R
import com.dlutrix.themoviewikicompose.data.model.MovieDetail
import com.dlutrix.themoviewikicompose.ui.component.InsetAwareTopAppBar
import com.dlutrix.themoviewikicompose.util.Resource
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int,
    movieTitle: String,
    contentPadding: PaddingValues,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBackPressed: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val movie = produceState<Resource<MovieDetail>>(initialValue = Resource.Loading(null)) {
        value = viewModel.getMovieDetail(movieId)
    }.value

    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            InsetAwareTopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.Start)
                    ) {
                        Text(
                            text = movieTitle,
                            style = MaterialTheme.typography.subtitle2,
                            color = LocalContentColor.current,
                            modifier = Modifier.padding(end = 16.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                            contentDescription = "Back button",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background
            )
        },
        modifier = Modifier
            .navigationBarsPadding()
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            when (movie) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Error -> {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            movie.error?.localizedMessage ?: "Unexpected Error",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            top = 8.dp,
                            bottom = 16.dp
                        )
                    ) {
                        item {
                            Poster(
                                imageUrl = "https://image.tmdb.org/t/p/original/${movie.data?.posterPath ?: ""}",
                                screenHeight = screenHeight,
                                runtime = movie.data?.runtime ?: 0,
                                releaseDate = movie.data?.releaseDate ?: "-",
                                voteAverage = movie.data?.voteAverage ?: 0.0
                            )
                        }

                        item {
                            Divider(
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }

                        item {
                            Overview(overview = movie.data?.overview ?: "-")
                        }

                        item {
                            Genre(genres = movie.data?.genres ?: listOf())
                        }

                        item {
                            Divider(
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp)
                            )
                        }

                        item {
                            Image(images = movie.data?.images?.backdrops ?: listOf())
                        }
                    }
                }
            }
        }
    }
}