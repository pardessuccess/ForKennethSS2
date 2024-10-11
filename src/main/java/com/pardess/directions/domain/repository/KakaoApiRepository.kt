package com.pardess.directions.domain.repository

import com.pardess.directions.data.Resource
import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLines

interface KakaoApiRepository {

    suspend fun getLocationList(): Resource<LocationListDto>

    suspend fun getRouteLines(
        origin: String,
        destination: String,
    ): Resource<List<RouteLines>>

    suspend fun getRouteInfo(
        origin: String,
        destination: String,
    ): Resource<RouteInfo>

}