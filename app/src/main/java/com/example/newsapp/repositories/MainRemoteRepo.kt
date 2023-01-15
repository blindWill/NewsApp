package com.example.newsapp.repositories

import com.example.newsapp.api.NewsAPI
import com.example.newsapp.data.Article
import com.example.newsapp.db.ArticleDao
import javax.inject.Inject

//@ActivityScoped
class MainRemoteRepo @Inject constructor(private val newsApi: NewsAPI, private val articleDao: ArticleDao) {

    suspend fun getBreakingNews(country: String) = newsApi.getBreakingNews(country)

    suspend fun searchForNews(searchQuery: String) = newsApi.searchForNews(searchQuery)

    suspend fun insert(article: Article) = articleDao.insert(article)

    fun getSavedNews() = articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)
}