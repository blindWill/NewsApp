package com.example.newsapp.repositories

import com.example.newsapp.api.NewsAPI
import javax.inject.Inject

//@ActivityScoped
class MainRemoteRepo @Inject constructor(private val newsApi: NewsAPI) {

    suspend fun getBreakingNews(country: String) = newsApi.getBreakingNews(country)
}