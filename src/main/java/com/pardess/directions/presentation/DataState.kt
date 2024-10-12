package com.pardess.directions.presentation

import com.pardess.directions.data.ErrorType

sealed class DataState {

    class Initial : DataState()

    class Loading : DataState()

    data class Success(val message: String) : DataState()

    data class Error(val message: String, val errorType: ErrorType) : DataState()
}