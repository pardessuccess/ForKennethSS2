package com.pardess.directions.domain.usecase.route

import com.pardess.directions.data.Resource
import com.pardess.directions.domain.model.RouteLines
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject

class GetRouteList @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(
        origin: String,
        destination: String
    ): Resource<List<RouteLines>> {
        return kakaoApiRepository.getRouteLines(
            origin = origin,
            destination = destination
        )
    }
}