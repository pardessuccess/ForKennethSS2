package com.pardess.directions.domain.usecase.distance_time

import com.pardess.directions.data.response.distance_time.RouteInfoDto
import com.pardess.directions.data.response.route.RouteListDto
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject

class GetRouteInfo @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(
        origin: String,
        destination: String
    ): RouteInfo {
        return kakaoApiRepository.getRouteInfo(
            origin = origin,
            destination = destination
        )
    }
}