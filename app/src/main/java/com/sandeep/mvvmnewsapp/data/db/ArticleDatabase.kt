package com.sandeep.mvvmnewsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sandeep.mvvmnewsapp.models.Article

/**
 * Created by SandeepY on 27052021
 **/


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDAO(): ArticleDAO
}