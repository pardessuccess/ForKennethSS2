package com.pardess.directions.presentation

data class State(
    val isLoading: Boolean = false,
    val isSuccess: String = "",
    val isError: String? = "",
)