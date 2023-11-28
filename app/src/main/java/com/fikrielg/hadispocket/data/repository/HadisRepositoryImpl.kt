package com.fikrielg.hadispocket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fikrielg.hadispocket.data.paging.HadisPagingSource
import com.fikrielg.hadispocket.data.source.remote.ApiInterface
import com.fikrielg.hadispocket.data.source.remote.model.AllBooksResponse
import com.fikrielg.hadispocket.data.source.remote.model.DetailHadisResponse
import com.fikrielg.hadispocket.data.source.remote.model.Hadith
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HadisRepositoryImpl(private val hadisApi: ApiInterface) : HadisRepository {
    override suspend fun getAllBooks(): AllBooksResponse {
        return hadisApi.getAllBooks()
    }

    override fun getListHadis(name: String): Flow<PagingData<Hadith>> = Pager(
        config = PagingConfig(pageSize = 300),
        pagingSourceFactory = { HadisPagingSource(hadisApi, name) }
    ).flow.flowOn(Dispatchers.Default)

    override suspend fun getDetailHadis(name: String, number: Int): DetailHadisResponse {
        return hadisApi.getDetailHadis(name, number)
    }

}