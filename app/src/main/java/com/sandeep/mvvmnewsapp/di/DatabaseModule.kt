package com.sandeep.mvvmnewsapp.di

import android.app.Application
import androidx.room.Room
import com.sandeep.mvvmnewsapp.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by SandeepY on 27052021
 **/


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        app: Application
    ): ArticleDatabase = Room.databaseBuilder(
        app,
        ArticleDatabase::class.java,
        "article_db.db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDao(
        database: ArticleDatabase
    ) = database.getArticleDAO()
}