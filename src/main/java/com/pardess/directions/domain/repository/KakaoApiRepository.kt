package com.pardess.directions.domain.repository

import com.pardess.directions.data.Result
import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLine

interface KakaoApiRepository {

    suspend fun getLocationList(): Result<LocationListDto>

    suspend fun getRouteLines(
        origin: String,
        destination: String,
    ): Result<List<RouteLine>>

    suspend fun getRouteInfo(
        origin: String,
        destination: String,
    ): Result<RouteInfo>

}