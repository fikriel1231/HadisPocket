package com.fikrielg.hadispocket.core.di

import com.fikrielg.hadispocket.core.data.source.remote.service.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providesRemoteDataSource(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.hadith.gading.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}