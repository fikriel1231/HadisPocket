package com.fikrielg.hadispocket.di

import com.fikrielg.hadispocket.data.repository.HadisRepository
import com.fikrielg.hadispocket.data.repository.HadisRepositoryImpl
import com.fikrielg.hadispocket.data.source.remote.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppModule {
    val hadisApi: ApiInterface
    val hadisRepository: HadisRepository
}

class AppModuleImpl : AppModule {
    override val hadisApi: ApiInterface
            by lazy {
                Retrofit.Builder()
                    .baseUrl("https://api.hadith.gading.dev/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)
            }

    override val hadisRepository: HadisRepository
            by lazy { HadisRepositoryImpl(hadisApi) }

}