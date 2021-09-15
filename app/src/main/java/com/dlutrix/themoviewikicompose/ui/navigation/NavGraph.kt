package com.dlutrix.themoviewikicompose.ui.navigation

sealed class NavGraph(val route: String) {

    object DiscoverScreen : NavGraph("discover_screen")
}
