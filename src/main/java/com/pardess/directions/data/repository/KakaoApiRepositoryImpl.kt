package com.pardess.directions.data.repository

import com.pardess.directions.data.DataMapper
import com.pardess.directions.data.network.KakaoApi
import com.pardess.directions.data.response.distance_time.RouteInfoDto
import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.data.response.route.RouteListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.model.RouteLines
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject

class KakaoApiRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApi,
) : KakaoApiRepository {
    override suspend fun getLocationList(): LocationListDto {
        return kakaoApi.getLocations()
    }

    override suspend fun getRouteLines(origin: String, destination: String): List<RouteLines> {
        val routeListDto = kakaoApi.getRoutes(
            origin = origin,
            destination = destination
        )
        return DataMapper.mapToWayLine(routeListDto)
    }

    override suspend fun getRouteInfo(origin: String, destination: String): RouteInfo {
        val routeInfoDto = kakaoApi.getDistanceTime(
            origin = origin,
            destination = destination
        )
        return DataMapper.mapToRouteInfo(routeInfoDto)
    }

}