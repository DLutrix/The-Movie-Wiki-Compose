package com.dlutrix.themoviewikicompose.ui.navigation

sealed class NavGraph(val route: String) {

    object DiscoverScreen : NavGraph("discover_screen")

    object MovieDetailScreen : NavGraph("movie_detail_screen") {

        const val routeWithArgument: String = "movie_detail_screen/{movieId}/{movieTitle}"

        const val movieId: String = "movieId"
        const val movieTitle: String = "movieTitle"
    }
}
