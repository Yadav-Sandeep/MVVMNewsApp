package com.sandeep.mvvmnewsapp.network

import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_API_KEY
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_API_KEY_VALUE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_COUNTRY
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_PAGE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_SEARCH_VALUE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by SandeepY on 27052021
 **/


interface NewsAPIService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @QueryMap queries: Map<String, String>
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query(QUERY_COUNTRY)
        country: String,
        @Query(QUERY_SEARCH_VALUE)
        searchQuery: String,
        @Query(QUERY_PAGE)
        pageNumber: Int,
        @Query(QUERY_API_KEY)
        apiKey: String = QUERY_API_KEY_VALUE
    ): Response<NewsResponse>
}