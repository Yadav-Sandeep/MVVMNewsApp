package com.sandeep.mvvmnewsapp.data.datasource

import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.network.NewsAPIService
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by SandeepY on 27052021
 **/


class RemoteDataSource @Inject constructor(
    private val newsAPI: NewsAPIService
) {

    suspend fun getNews(queries: Map<String, String>): Response<NewsResponse> {
        return newsAPI.getTopHeadlines(queries)
    }

    suspend fun getSearchedNews(country: String, query: String, page: Int): Response<NewsResponse> {
        return newsAPI.getSearchedTopHeadlines(country, query, page)
    }
}