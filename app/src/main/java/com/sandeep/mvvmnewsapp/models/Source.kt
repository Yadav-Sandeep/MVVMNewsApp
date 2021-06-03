package com.sandeep.mvvmnewsapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by SandeepY on 27052021
 **/


data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
) : Serializable