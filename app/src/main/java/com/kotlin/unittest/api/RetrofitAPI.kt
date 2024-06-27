package com.kotlin.unittest.api

import com.kotlin.unittest.BuildConfig
import com.kotlin.unittest.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.PIXABAY_KEY
    ): Response<ImageResponse>
}