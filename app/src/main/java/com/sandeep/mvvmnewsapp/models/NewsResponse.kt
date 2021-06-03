package com.sandeep.mvvmnewsapp.models

import com.google.gson.annotations.SerializedName

/**
 * Created by SandeepY on 27052021
 **/


data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String
)