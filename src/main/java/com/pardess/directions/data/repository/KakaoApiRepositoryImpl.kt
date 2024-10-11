package com.pardess.directions.data.repository

import com.pardess.directions.data.DataMapper
import com.pardess.directions.data.ErrorType
import com.pardess.directions.data.Resource
import com.pardess.directions.data.network.KakaoApi
import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLines
import com.pardess.directions.domain.repository.KakaoApiRepository
import retrofit2.HttpException
import javax.inject.Inject

class KakaoApiRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApi,
) : KakaoApiRepository {
    override suspend fun getLocationList(): Resource<LocationListDto> {
        val resource = try {
            val response = kakaoApi.getLocations()
            Resource.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Resource.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Resource.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Resource.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Resource.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Resource.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Resource.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Resource.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return resource
    }

    override suspend fun getRouteLines(
        origin: String,
        destination: String
    ): Resource<List<RouteLines>> {
        val resource = try {
            val response = DataMapper.mapToWayLine(
                kakaoApi.getRoutes(
                    origin = origin,
                    destination = destination
                )
            )
            Resource.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Resource.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Resource.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Resource.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Resource.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Resource.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Resource.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Resource.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return resource
    }

    override suspend fun getRouteInfo(origin: String, destination: String): Resource<RouteInfo> {
        val resource = try {
            val response =
                DataMapper.mapToRouteInfo(
                    kakaoApi.getDistanceTime(
                        origin = origin,
                        destination = destination
                    )
                )
            Resource.Success(response)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Resource.Error(
                            message = "Bad Request",
                            errorType = ErrorType.BAD_REQUEST_ERROR
                        )
                    } else if (e.code() == 401) {
                        Resource.Error(
                            message = "Unauthorized",
                            errorType = ErrorType.AUTHENTICATION_ERROR
                        )
                    } else if (e.code() == 404) {
                        Resource.Error(
                            message = "Resource not found",
                            errorType = ErrorType.RESOURCE_NOT_FOUND
                        )
                    } else if (e.code() == 429) {
                        Resource.Error(
                            message = "Rate limit exceeded",
                            errorType = ErrorType.RATE_LIMIT_EXCEEDED
                        )
                    } else if (e.code() == 500) {
                        Resource.Error(
                            message = "Internal Server Error",
                            errorType = ErrorType.SERVER_ERROR
                        )
                    } else {
                        Resource.Error(
                            message = "HTTP Error: ${e.code()}",
                            errorType = ErrorType.HTTP_ERROR
                        )
                    }
                }

                else -> {
                    Resource.Error(
                        message = e.message ?: "Unknown error occuerred",
                        errorType = ErrorType.UNKNOWN_ERROR
                    )
                }
            }
        }
        return resource
    }

}