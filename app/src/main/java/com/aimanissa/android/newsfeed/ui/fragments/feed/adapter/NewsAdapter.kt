package com.aimanissa.android.newsfeed.ui.fragments.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.databinding.NewsItemBinding
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    private var newsItems = mutableListOf<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsItems[position])
    }

    override fun getItemCount(): Int = newsItems.size

    fun updateNews(updatedNews: List<NewsItem>) {
        newsItems.clear()
        newsItems = updatedNews.toMutableList()
        notifyDataSetChanged()
    }

    inner class NewsHolder(val newsItemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsItemBinding.root) {

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(newsItems[adapterPosition].title)
            }
        }

        fun bind(newsItem: NewsItem) {
            newsItemBinding.newsTitle.text = newsItem.title
            newsItemBinding.newsDescription.text = newsItem.description
            Picasso.get()
                .load(newsItem.urlToImage)
                .into(newsItemBinding.newsImageView)
        }
    }
}













