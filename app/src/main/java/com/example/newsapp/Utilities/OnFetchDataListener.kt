package com.example.newsapp.Utilities

import com.example.newsapp.Models.Article

interface OnFetchDataListener<NewsApiResponse> {
    fun onFetchData(list: List<Article>, message: String)
    fun onError(message: String)
}