package com.pardess.directions.domain.usecase.distance_time

import com.pardess.directions.data.Resource
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject

class GetRouteInfo @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(
        origin: String,
        destination: String
    ): Resource<RouteInfo> {
        return kakaoApiRepository.getRouteInfo(
            origin = origin,
            destination = destination
        )
    }
}