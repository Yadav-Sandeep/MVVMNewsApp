package com.sandeep.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandeep.mvvmnewsapp.databinding.NewsItemBinding
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.utils.NewsDiffutil

/**
 * Created by SandeepY on 27052021
 **/


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList = emptyList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = newsList[position]
        holder.bind(currentNews)
    }

    fun setData(newData: NewsResponse) {
        val recipesDiffUtil = NewsDiffutil(newsList, newData.articles)
        val difUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        newsList = newData.articles
        difUtilResult.dispatchUpdatesTo(this)
    }

    fun setSavedData(newData: List<Article>) {
        val recipesDiffUtil = NewsDiffutil(newsList, newData)
        val difUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        newsList = newData
        difUtilResult.dispatchUpdatesTo(this)
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.articleBean = article

            binding.root.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}