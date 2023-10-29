package com.fikrielg.hadispocket.core.data.repository

import com.fikrielg.hadispocket.core.data.source.remote.service.ApiInterface
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getAllBooks() = apiInterface.getAllBooks()
    suspend fun getListHadisFromBook(name: String) =
        apiInterface.getListHadisFromBook(name)

    suspend fun getDetailHadis(name: String, number: Int) = apiInterface.getDetailHadis(name, number)
}