package com.pardess.directions.domain.model

import com.kakao.vectormap.LatLng

data class RouteLine(
    val wayList: List<LatLng>,
    val trafficState: TrafficState
)
