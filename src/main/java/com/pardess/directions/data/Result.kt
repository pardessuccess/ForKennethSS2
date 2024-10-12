package com.pardess.directions.data

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val errorType: ErrorType = ErrorType.UNKNOWN_ERROR
) {

    class Success<T>(data: T) : Result<T>(data)

    class Error<T>(message: String, errorType: ErrorType) :
        Result<T>(message = message, errorType = errorType)

    override fun toString(): String {
        return "Resource(data=$data, message=$message, errorType=$errorType)"
    }
}

enum class ErrorType {
    NETWORK_ERROR,
    HTTP_ERROR,
    UNKNOWN_ERROR,
    BAD_REQUEST_ERROR,
    AUTHENTICATION_ERROR,
    RATE_LIMIT_EXCEEDED,
    SERVER_ERROR,
    TIMEOUT_ERROR,
    RESOURCE_NOT_FOUND
}