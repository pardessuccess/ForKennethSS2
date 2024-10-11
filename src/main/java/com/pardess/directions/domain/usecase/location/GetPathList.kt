package com.pardess.directions.domain.usecase.location

import com.pardess.directions.data.response.location.LocationListDto
import com.pardess.directions.domain.repository.KakaoApiRepository
import javax.inject.Inject


class GetPathList @Inject constructor(
    private val kakaoApiRepository: KakaoApiRepository
) {
    suspend operator fun invoke(): LocationListDto {
        return kakaoApiRepository.getLocationList()
    }
}