package com.example.newsapp.adapter

import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.Article
import com.example.newsapp.databinding.NewsItemBinding

class NewsAdapter(val context: Context, private val listener: Listener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article, listener: Listener) {
            binding.apply { //with(binding)
                Glide.with(context).load(item.urlToImage).into(ArticleImageIV)
                TitleTV.text = item.title
                AuthorTV.text = item.author
                DescriptionTV.text = item.content
                PublishedTimeTV.text = item.publishedAt
                itemView.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }

    }

    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface Listener{
        fun onClick(article: Article)
    }

}