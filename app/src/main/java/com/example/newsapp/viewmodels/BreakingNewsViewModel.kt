package com.example.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.repositories.MainRemoteRepo
import com.example.newsapp.utils.Constants.COUNTRY_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private val repo: MainRemoteRepo): ViewModel() {

    val breakingNews = MutableLiveData<NewsResponse>()

    val dataForRecycler = MutableLiveData<ArrayList<Article>>()

    val searchNews = MutableLiveData<NewsResponse>()

    fun fetchBreakingNews(){
        viewModelScope.launch {
            val response = repo.getBreakingNews(COUNTRY_CODE)
            if (response.isSuccessful) {
                breakingNews.postValue(response.body())
            } else {
                Log.d(
                    "Network log",
                    "fetchBreakingNews: code: ${response.code()} message: ${response.message()} "
                )
            }
        }
    }

    fun updateDataForRecycler(newData: NewsResponse) {
        val newList = newData.articles
        dataForRecycler.postValue(newList)
    }


    fun saveArticle(article: Article) = viewModelScope.launch {
        repo.insert(article)
    }

    fun getSavedNews() = repo.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repo.deleteArticle(article)
    }

    fun searchForNews(searchQuery: String){
        viewModelScope.launch {
            val response = repo.searchForNews(searchQuery)
            if (response.isSuccessful) {
                searchNews.postValue(response.body())
            } else {
                Log.d(
                    "Network log",
                    "searchForNews: code: ${response.code()} message: ${response.message()} "
                )
            }
        }
    }
}