package com.fikrielg.hadispocket

import android.app.Application
import com.fikrielg.hadispocket.data.repository.HadisRepository
import com.fikrielg.hadispocket.data.repository.HadisRepositoryImpl
import com.fikrielg.hadispocket.data.source.remote.ApiInterface
import com.fikrielg.hadispocket.di.AppModule
import com.fikrielg.hadispocket.di.AppModuleImpl

class HadisPocketApplication:Application() {
    companion object{
        lateinit var repository: HadisRepository
        private lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl()
        repository = HadisRepositoryImpl(appModule.hadisApi)
    }
}