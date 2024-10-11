package com.pardess.directions.not_use

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pardess.directions.presentation.navigation.DirectionNavGraph

@Composable
fun DirectionApp() {
    val directionNavController = rememberNavController()
    DirectionNavGraph(directionNavController = directionNavController)
}