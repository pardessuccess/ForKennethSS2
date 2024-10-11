package com.pardess.directions.presentation

import com.pardess.directions.domain.model.RouteInfo

object Utils {


    fun setRouteInfo(routeInfo: RouteInfo): Pair<String, String> {
        return Pair(
            setTime(routeInfo.hour, routeInfo.minute, routeInfo.second),
            meterWithComma(routeInfo.distance)
        )
    }

    private fun meterWithComma(number: Int): String {
        val distanceString = StringBuilder("거리 : ")
        distanceString.append(String.format("%,d", number))
        distanceString.append(" m")

        return distanceString.toString().trim()
    }

    private fun setTime(hour: Int, minute: Int, second: Int): String {

        val timeString = StringBuilder("소요 시간 : ")

        if (hour > 0) {
            timeString.append(String.format("%d시간 ", hour))
        }

        if (minute > 0) {
            timeString.append(String.format("%1d분 ", minute))
        }

        timeString.append(String.format("%1d초", second))

        return timeString.toString().trim()
    }

}