package com.pardess.directions.data.response.distance_time

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteInfoDto(
    val distance: Int,
    val time: Int
) : Parcelable