package com.pardess.directions.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object DestinationRoute {
    const val MAIN_ROUTE = "home"
}

@Composable
fun rememberDestinationController(
    navHostController: NavHostController = rememberNavController()
) {


}