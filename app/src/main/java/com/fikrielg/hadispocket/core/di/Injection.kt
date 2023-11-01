package com.fikrielg.hadispocket.core.di

import com.fikrielg.hadispocket.core.data.source.remote.service.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object Injection {
    private const val BASE_URL = "https://api.hadith.gading.dev/"
    fun providesRemoteDataSource() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiInterface::class.java)
}
