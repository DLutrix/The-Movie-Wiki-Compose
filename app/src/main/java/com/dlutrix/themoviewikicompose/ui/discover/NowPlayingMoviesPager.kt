package com.dlutrix.themoviewikicompose.ui.discover

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.dlutrix.themoviewikicompose.R
import com.dlutrix.themoviewikicompose.data.entity.Movie
import com.dlutrix.themoviewikicompose.ui.theme.BlackRs
import com.dlutrix.themoviewikicompose.ui.theme.Shamrock
import com.dlutrix.themoviewikicompose.ui.theme.SilverChalice
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun NowPlayingMoviesPager(
    movies: List<Movie>,
    height: Int,
    navController: NavController
) {
    val pagerState = rememberPagerState(
        pageCount = movies.size,
        infiniteLoop = false,
    )

    var titleVisible by remember {
        mutableStateOf(false)
    }

    val pagerHeight = height * 0.73


    LaunchedEffect(pagerState.currentPage) {
        launch {
            titleVisible = true
            delay(3000)
            titleVisible = false
            with(pagerState) {
                val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                animateScrollToPage(
                    page = target,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(pagerHeight.dp),
    ) { page ->
        NowPlayingMoviesItem(
            movie = movies[page],
            pagerState = pagerState,
            height = pagerHeight.toFloat(),
            titleVisible = titleVisible,
            navController = navController
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
private fun NowPlayingMoviesItem(
    movie: Movie?,
    pagerState: PagerState,
    height: Float,
    titleVisible: Boolean,
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate(
                    "movie_detail_screen/${movie?.id}/${movie?.title}"
                )
            }
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/original/${movie?.backdropPath}",
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "${movie?.title} image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = height * 0.2f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height((height * 0.9f).dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                AnimatedVisibility(
                    visible = titleVisible,
                    enter = slideInVertically(
                        initialOffsetY = {
                            -40
                        }
                    ) + expandVertically(
                        expandFrom = Alignment.Bottom
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                ) {
                    Text(
                        text = movie?.title ?: "",
                        color = Color.White,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily(Font(R.font.nunito_bold)),
                        fontSize = 24.sp
                    )
                }

                AnimatedVisibility(
                    visible = titleVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = {
                            -40
                        }
                    ) + expandVertically(
                        expandFrom = Alignment.Bottom
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutHorizontally() + shrinkHorizontally(shrinkTowards = Alignment.Start) + fadeOut()
                ) {
                    Text(
                        text = movie?.overview ?: "",
                        color = SilverChalice,
                        fontWeight = FontWeight.W400,
                        modifier = Modifier.padding(top = 8.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                            bottom = 16.dp,
                            start = 2.dp
                        ),
                    inactiveColor = Color.White,
                    activeColor = MaterialTheme.colors.primary
                )
            }
        }
    }
}