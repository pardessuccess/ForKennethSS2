package com.pardess.directions.data.di

import com.pardess.directions.data.DataMapper
import com.pardess.directions.data.repository.KakaoApiRepositoryImpl
import com.pardess.directions.domain.repository.KakaoApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun provideKakaoApiRepository(
        kakaoApiRepositoryImpl: KakaoApiRepositoryImpl,
    ): KakaoApiRepository

}