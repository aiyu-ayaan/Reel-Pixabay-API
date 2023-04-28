package com.atech.reel.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class PixaRepository @Inject constructor(
    private val api: PixaInterface
) {

    fun getVideos(query: String) =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
            pagingSourceFactory = { PixaPagingSource(query, api) })
            .flow
}