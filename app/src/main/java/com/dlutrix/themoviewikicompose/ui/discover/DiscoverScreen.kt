package com.dlutrix.themoviewikicompose.ui.discover

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.dlutrix.themoviewikicompose.util.Resource
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel,
    contentPadding: PaddingValues,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    val upcomingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val nowPlayingMovies by viewModel.nowPlayingMovies.observeAsState()

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            UpcomingMovies(
                movies = upcomingMovies,
                isExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                refreshDiscoverMovies = viewModel::refreshNowPlayingMovies,
                navController = navController,
                contentPadding = contentPadding
            )
        },
        sheetPeekHeight = (screenHeight * 0.35f).dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier
            .navigationBarsPadding()
            .padding(top = contentPadding.calculateTopPadding()),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            when (nowPlayingMovies) {
                is Resource.Error -> {
                    if (nowPlayingMovies?.data?.isNullOrEmpty()!!) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = nowPlayingMovies!!.error!!.message!!)
                            }
                        }
                    } else {
                        Box {
                            NowPlayingMoviesPager(
                                movies = nowPlayingMovies!!.data!!,
                                height = screenHeight,
                                navController = navController
                            )
                        }
                    }
                }
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    Box {
                        NowPlayingMoviesPager(
                            movies = nowPlayingMovies!!.data!!,
                            height = screenHeight,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}