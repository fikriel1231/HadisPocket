package com.fikrielg.hadispocket.data.repository

import androidx.paging.PagingData
import com.fikrielg.hadispocket.data.source.remote.model.AllBooksResponse
import com.fikrielg.hadispocket.data.source.remote.model.DetailHadisResponse
import com.fikrielg.hadispocket.data.source.remote.model.Hadith
import com.fikrielg.hadispocket.data.source.remote.model.ListHadisFromBookResponse
import kotlinx.coroutines.flow.Flow

interface HadisRepository{
    suspend fun getAllBooks():AllBooksResponse
     fun getListHadis(name: String): Flow<PagingData<Hadith>>

    suspend fun getDetailHadis(name: String, number: Int):DetailHadisResponse
}

