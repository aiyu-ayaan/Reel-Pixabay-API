package com.atech.reel.data

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PixaPagingSource(
    private val query: String,
    private val api: PixaInterface
) : PagingSource<Int, Hits>() {
    override fun getRefreshKey(state: PagingState<Int, Hits>): Int? =
        state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hits> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = api.getVideos(query, nextPageNumber, params.loadSize)
            LoadResult.Page(
                data = response.hits,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.hits.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}