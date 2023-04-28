package com.atech.reel.data

import retrofit2.http.GET
import retrofit2.http.Query


interface PixaInterface {

    companion object {
        const val BASE_URL = "https://pixabay.com/api/"
        const val API_KEY = "35834179-dcc40977a1d5f934000d1d2e7"
    }

    @GET("videos/?key=$API_KEY")
    suspend fun getVideos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): PixaResponse

}