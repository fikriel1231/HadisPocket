package com.fikrielg.hadispocket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fikrielg.hadispocket.data.source.remote.ApiInterface
import com.fikrielg.hadispocket.data.source.remote.model.Hadith

class HadisPagingSource(
    private val apiService: ApiInterface,
    private val name: String,
) : PagingSource<Int, Hadith>() {
    override fun getRefreshKey(state: PagingState<Int, Hadith>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(
                anchorPosition
            )?.prevKey?.plus(300)?: state.closestPageToPosition(anchorPosition)?.prevKey?.minus(300)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hadith> {
        return try {
            val page = params.key ?: 300
            val range = "1-${page}"
            val response = apiService.getListHadis(
                name, range
            )
            LoadResult.Page(
                data = response.data.hadiths,
                prevKey = if (range == "1-300") null else page.minus(300),
                nextKey = if(response.data.hadiths.isEmpty()) null else page.plus(300)
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}