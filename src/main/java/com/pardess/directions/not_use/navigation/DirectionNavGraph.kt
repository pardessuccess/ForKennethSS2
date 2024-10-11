package com.pardess.directions.not_use.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pardess.directions.not_use.LocationListScreen
import com.pardess.directions.not_use.navgraph.DestinationRoute.MAIN_ROUTE


@Composable
fun DirectionNavGraph(
    directionNavController: NavHostController
) {
    NavHost(
        navController = directionNavController,
        startDestination = MAIN_ROUTE
    ) {
        navGraph()
    }

}

fun NavGraphBuilder.navGraph() {
    navigation(
        route = MAIN_ROUTE,
        startDestination = Screen.LocationList.route
    ) {
        composable(route = Screen.LocationList.route) {
            LocationListScreen(
                viewModel = hiltViewModel()
            )
        }
    }
}