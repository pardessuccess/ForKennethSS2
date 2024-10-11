package com.pardess.directions.presentation

import com.pardess.directions.data.ErrorType

sealed class DataState {

    class Loading : DataState()

    class Success : DataState()

    data class Error(val message: String, val errorType: ErrorType) : DataState()
}