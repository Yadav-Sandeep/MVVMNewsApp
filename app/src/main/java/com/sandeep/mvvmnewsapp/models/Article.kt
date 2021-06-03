package com.sandeep.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by SandeepY on 27052021
 **/


@Entity(
    tableName = "articles"
)
data class Article(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @SerializedName("author")
    val author: String?,

    @SerializedName("content")
    val content: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("publishedAt")
    val publishedAt: String?,

    @SerializedName("source")
    val source: Source?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String
) : Serializable