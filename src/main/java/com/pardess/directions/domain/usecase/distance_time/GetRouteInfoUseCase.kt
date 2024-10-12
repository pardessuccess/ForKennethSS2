package com.pardess.directions.domain.usecase.distance_time

import com.pardess.directions.data.Result
import com.pardess.directions.domain.model.RouteInfo
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject

class GetRouteInfoUseCase @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(
        origin: String,
        destination: String
    ): Result<RouteInfo> {
        return kakaoApiRepository.getRouteInfo(
            origin = origin,
            destination = destination
        )
    }
}