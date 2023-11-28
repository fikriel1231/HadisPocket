package com.fikrielg.hadispocket.data.source.remote

import androidx.paging.PagingData
import com.fikrielg.hadispocket.data.source.remote.model.AllBooksResponse
import com.fikrielg.hadispocket.data.source.remote.model.DetailHadisResponse
import com.fikrielg.hadispocket.data.source.remote.model.Hadith
import com.fikrielg.hadispocket.data.source.remote.model.ListHadisFromBookResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("books")
    suspend fun getAllBooks(): AllBooksResponse

    @GET("books/{name}")
    suspend fun getListHadis(
        @Path("name") name:String,
        @Query("range") range: String
    ): ListHadisFromBookResponse

    @GET("books/{name}/{number}")
    suspend fun getDetailHadis(
        @Path("name") name: String,
        @Path("number") number: Int,
    ): DetailHadisResponse
}