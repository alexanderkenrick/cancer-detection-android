package com.dicoding.asclepius.retrofit

import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    fun getNews(
        @Query("q") search: String = "cancer",
        @Query("category") category: String = "health",
        @Query("language") language: String = "en",
        @Query("apiKey") key: String = BuildConfig.API_KEY
    ): Call<NewsResponse>
}