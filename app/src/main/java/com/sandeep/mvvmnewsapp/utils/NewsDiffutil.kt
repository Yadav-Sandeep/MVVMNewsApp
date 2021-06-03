package com.sandeep.mvvmnewsapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.sandeep.mvvmnewsapp.models.Article

/**
 * Created by SandeepY on 27052021
 **/


class NewsDiffutil(
    private val oldList: List<Article>,
    private val newList: List<Article>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}