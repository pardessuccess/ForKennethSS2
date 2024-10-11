package com.pardess.directions.presentation

import com.pardess.directions.data.ErrorType
import com.pardess.directions.data.Resource

sealed class DataState(
    message: String? = null,
    errorType: ErrorType = ErrorType.UNKNOWN_ERROR
) {

    class Loading : DataState()

    class Success : DataState()

    data class Error(val message: String, val errorType: ErrorType) :
        DataState(message = message, errorType = errorType)
}