package com.example.newsapp.adapter

import com.example.newsapp.data.Article

interface Listener {
    fun onClick(article: Article)
}