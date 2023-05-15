package com.example.newsapp.Utilities

import com.example.newsapp.Models.NewsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getNews(
        @Query("country") country:String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_key:String
    ): Call<NewsApiResponse>
}