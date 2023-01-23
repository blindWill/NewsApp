package com.example.newsapp.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.repositories.MainRemoteRepo
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.COUNTRY_CODE
import retrofit2.HttpException

class NewsPagingSource (
    private val repository: MainRemoteRepo
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getBreakingNews(COUNTRY_CODE, currentPage)
            val data = response.body()!!.articles
            val responseData = mutableListOf<Article>()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}