package com.sandeep.mvvmnewsapp.network

import com.sandeep.mvvmnewsapp.data.datasource.LocalDataSource
import com.sandeep.mvvmnewsapp.data.datasource.RemoteDataSource
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by SandeepY on 27052021
 **/


class NewsRepository @Inject constructor(
    private val newsRemoteDatasource: RemoteDataSource,
    private val newsLocalDataSource: LocalDataSource
) {
    suspend fun getNewsHeadlines(queries: Map<String, String>): Resource<NewsResponse> {
        return handleAndConvertResponse(newsRemoteDatasource.getNews(queries))
    }

    suspend fun getSearchedNewsHeadlines(country: String, query: String, page: Int): Resource<NewsResponse> {
        return handleAndConvertResponse(newsRemoteDatasource.getSearchedNews(country, query, page))
    }

    suspend fun saveNews(article: Article): Long {
        return newsLocalDataSource.saveNews(article)
    }

    suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteNews(article)
    }

    fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedNews()
    }

    fun getArticle(url: String): Flow<Article> {
        return newsLocalDataSource.getArticle(url)
    }

    private fun handleAndConvertResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            if (response.body()?.status == "ok") {
                response.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
            return Resource.Error("Error : " + response.message(), null)
        }
        return try {
            val jObjError = JSONObject(response.errorBody()!!.string())
            Resource.Error("Error : " + jObjError.getString("message"), null)
        } catch (e: Exception) {
            Resource.Error("Error : " + e.message.toString(), null)
        }
    }
}