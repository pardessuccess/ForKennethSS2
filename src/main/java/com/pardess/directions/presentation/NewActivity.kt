package com.pardess.directions.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewActivity : ComponentActivity() {

    private val viewModel by viewModels<DirectionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DirectionApp(
                viewModel = viewModel
            )
        }
    }
}