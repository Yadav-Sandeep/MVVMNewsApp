package com.sandeep.mvvmnewsapp.utils

/**
 * Created by SandeepY on 27052021
 **/


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null

) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}