package com.pardess.directions.data.repository

import com.pardess.directions.data.DataMapper
import com.pardess.directions.data.ErrorType
import com.pardess.directions.data.Result
import com.pardess.directions.data.network.KakaoApi
import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLine
import com.pardess.directions.domain.repository.KakaoApiRepository
import retrofit2.HttpException
import javax.inject.Inject

class KakaoApiRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApi,
) : KakaoApiRepository {
    override suspend fun getLocationList(): Result<LocationListDto> {
        val result = try {
            val response = kakaoApi.getLocations()
            Result.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Result.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Result.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Result.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Result.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Result.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Result.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Result.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return result
    }

    override suspend fun getRouteLines(
        origin: String,
        destination: String
    ): Result<List<RouteLine>> {
        val result = try {
            val response = DataMapper.mapToWayLine(
                kakaoApi.getRoutes(
                    origin = origin,
                    destination = destination
                )
            )
            Result.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Result.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Result.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Result.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Result.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Result.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Result.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Result.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return result
    }

    override suspend fun getRouteInfo(origin: String, destination: String): Result<RouteInfo> {
        val result = try {
            val response =
                DataMapper.mapToRouteInfo(
                    kakaoApi.getDistanceTime(
                        origin = origin,
                        destination = destination
                    )
                )
            Result.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Result.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Result.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Result.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Result.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Result.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Result.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Result.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return result
    }

}