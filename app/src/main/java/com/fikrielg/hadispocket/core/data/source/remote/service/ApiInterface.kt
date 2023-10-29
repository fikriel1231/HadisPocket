package com.fikrielg.hadispocket.core.data.source.remote.service

import com.fikrielg.hadispocket.core.model.AllBooksResponse
import com.fikrielg.hadispocket.core.model.DetailHadisResponse
import com.fikrielg.hadispocket.core.model.ListHadisFromBookResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("books")
    suspend fun getAllBooks(): AllBooksResponse

    @GET("books/{name}?range=1-300")
    suspend fun getListHadisFromBook(
        @Path("name") name:String,
//        @Path("startNumber") startNumber: Int?,
//        @Path("endNumber") endNumber: Int?,
    ): ListHadisFromBookResponse

    @GET("books/{name}/{number}")
    suspend fun getDetailHadis(
        @Path("name") name: String,
        @Path("number") number: Int,
    ): DetailHadisResponse
}