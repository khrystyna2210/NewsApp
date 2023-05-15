package com.example.newsapp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsApiResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>
): Serializable