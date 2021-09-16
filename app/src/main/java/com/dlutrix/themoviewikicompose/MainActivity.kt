package com.dlutrix.themoviewikicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.dlutrix.themoviewikicompose.ui.detail.MovieDetailScreen
import com.dlutrix.themoviewikicompose.ui.discover.DiscoverScreen
import com.dlutrix.themoviewikicompose.ui.navigation.NavGraph
import com.dlutrix.themoviewikicompose.ui.theme.TheMovieWikiComposeTheme
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalPagingApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TheMovieWikiComposeTheme {
                val systemUiController = rememberSystemUiController()
                val navigationBarColor = MaterialTheme.colors.surface
                val useDarkIcons = MaterialTheme.colors.isLight

                val contentPaddings = rememberInsetsPaddingValues(
                    insets = LocalWindowInsets.current.navigationBars,
                    applyTop = true
                )

                val navController = rememberNavController()

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                    systemUiController.setNavigationBarColor(
                        color = navigationBarColor,
                        darkIcons = useDarkIcons
                    )
                }

                ProvideWindowInsets {
                    NavHost(
                        navController = navController,
                        startDestination = NavGraph.DiscoverScreen.route
                    ) {
                        composable(NavGraph.DiscoverScreen.route) {
                            DiscoverScreen(
                                viewModel = hiltViewModel(),
                                contentPadding = contentPaddings,
                                navController = navController
                            )
                        }
                        composable(
                            route = NavGraph.MovieDetailScreen.routeWithArgument,
                            arguments = listOf(
                                navArgument(NavGraph.MovieDetailScreen.movieId) {
                                    type = NavType.IntType
                                },
                                navArgument(NavGraph.MovieDetailScreen.movieTitle) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val movieId = it.arguments?.getInt(NavGraph.MovieDetailScreen.movieId)
                                ?: return@composable

                            val movieTitle =
                                it.arguments?.getString(NavGraph.MovieDetailScreen.movieTitle)
                                    ?: return@composable

                            MovieDetailScreen(
                                viewModel = hiltViewModel(),
                                movieId = movieId,
                                movieTitle = movieTitle,
                                contentPadding = contentPaddings,
                                onBackPressed = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}