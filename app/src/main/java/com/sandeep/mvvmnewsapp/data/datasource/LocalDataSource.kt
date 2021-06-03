package com.sandeep.mvvmnewsapp.data.datasource

import com.sandeep.mvvmnewsapp.data.db.ArticleDAO
import com.sandeep.mvvmnewsapp.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by SandeepY on 27052021
 **/


class LocalDataSource @Inject constructor(
    private val recipesDao: ArticleDAO
) {
    suspend fun saveNews(article: Article): Long {
        return recipesDao.upsert(article)
    }

    suspend fun deleteNews(article: Article) {
        recipesDao.deleteArticle(article)
    }

    fun getSavedNews(): Flow<List<Article>> {
        return recipesDao.getAllArticles()
    }

    fun getArticle(url: String): Flow<Article> {
        return recipesDao.getArticle(url)
    }
}