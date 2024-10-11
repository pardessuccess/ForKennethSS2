package com.pardess.directions

import android.app.Application
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DirectionsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoMapSdk.init(this, Constants.KAKAO_MAP_KEY)

    }

}