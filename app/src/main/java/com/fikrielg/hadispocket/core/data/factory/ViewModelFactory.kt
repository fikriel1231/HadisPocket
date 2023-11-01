package com.fikrielg.hadispocket.core.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fikrielg.hadispocket.core.data.repository.MainRepository
import com.fikrielg.hadispocket.core.data.source.remote.service.ApiInterface
import com.fikrielg.hadispocket.core.di.Injection
import com.fikrielg.hadispocket.ui.screen.hadistfrombook.HadisFromBookViewModel
import com.fikrielg.hadispocket.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(HadisFromBookViewModel::class.java) -> HadisFromBookViewModel(repository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null
            fun getInstance(): ViewModelFactory = INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(MainRepository(Injection.providesRemoteDataSource()))
            }.also { INSTANCE = it }
        }

}