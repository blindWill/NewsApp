package com.example.newsapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.utils.Constants.ARTICLE_TABLE
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = ARTICLE_TABLE
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("content") var content: String? = null
): java.io.Serializable