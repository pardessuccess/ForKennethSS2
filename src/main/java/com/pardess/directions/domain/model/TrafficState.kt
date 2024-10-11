package com.pardess.directions.domain.model

import com.pardess.directions.R

enum class TrafficState(val styleRes: Int) {
    UNKNOWN(R.style.UnknownRouteLineStyle),
    JAM(R.style.JamRouteLineStyle),
    DELAY(R.style.DelayRouteLineStyle),
    SLOW(R.style.SlowRouteLineStyle),
    NORMAL(R.style.NormalRouteLineStyle),
    BLOCK(R.style.BlockRouteLineStyle)
}