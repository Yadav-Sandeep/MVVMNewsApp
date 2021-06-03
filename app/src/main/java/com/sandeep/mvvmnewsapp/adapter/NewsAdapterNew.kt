package com.sandeep.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sandeep.mvvmnewsapp.databinding.NewsItemBinding
import com.sandeep.mvvmnewsapp.models.Article

/**
 * Created by SandeepY on 30052021
 **/


class NewsAdapterNew : RecyclerView.Adapter<NewsAdapterNew.NewsViewHolderNew>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterNew.NewsViewHolderNew {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolderNew(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsAdapterNew.NewsViewHolderNew, position: Int) {
        val currentNews = differ.currentList[position]
        holder.bind(currentNews)
    }


    inner class NewsViewHolderNew(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
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