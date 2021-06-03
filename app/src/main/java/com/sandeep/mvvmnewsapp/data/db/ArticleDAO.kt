package com.sandeep.mvvmnewsapp.data.db

import androidx.room.*
import com.sandeep.mvvmnewsapp.models.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by SandeepY on 27052021
 **/


@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles order by 1 DESC")
    fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM articles where url =:url")
    fun getArticle(url: String): Flow<Article>

    @Delete
    suspend fun deleteArticle(article: Article)
}