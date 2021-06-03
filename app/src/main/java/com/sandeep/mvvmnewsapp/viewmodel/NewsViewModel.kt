package com.sandeep.mvvmnewsapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.network.NewsRepository
import com.sandeep.mvvmnewsapp.utils.CommonUtil
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_API_KEY
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_API_KEY_VALUE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_COUNTRY
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_COUNTRY_VALUE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_PAGE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_PAGE_VALUE
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_SEARCH
import com.sandeep.mvvmnewsapp.utils.Constants.Companion.QUERY_SEARCH_VALUE
import com.sandeep.mvvmnewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

/**
 * Created by SandeepY on 27052021
 **/


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    application: Application
) : AndroidViewModel(application) {

    private val breakingNewsMutableLiveData: MutableLiveData<Resource<NewsResponse>> =
        MutableLiveData()

    val breakingNewsData: MutableLiveData<Resource<NewsResponse>>
        get() = breakingNewsMutableLiveData

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_COUNTRY] = QUERY_COUNTRY_VALUE
        queries[QUERY_PAGE] = QUERY_PAGE_VALUE
        queries[QUERY_API_KEY] = QUERY_API_KEY_VALUE
        return queries
    }

    fun applySearchedQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_COUNTRY] = QUERY_COUNTRY_VALUE
        queries[QUERY_PAGE] = QUERY_PAGE_VALUE
        queries[QUERY_SEARCH] = QUERY_SEARCH_VALUE
        queries[QUERY_API_KEY] = QUERY_API_KEY_VALUE
        return queries
    }

    fun getBreakingNews() = viewModelScope.launch {
        try {
            if (CommonUtil().hasInternetConnection(getApplication())) {
                breakingNewsMutableLiveData.postValue(Resource.Loading())
                val response = newsRepository.getNewsHeadlines(applyQueries())
                breakingNewsMutableLiveData.postValue(response)
            } else {
                breakingNewsMutableLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    breakingNewsMutableLiveData.postValue(Resource.Error("Network failure"))
                else ->
                    breakingNewsMutableLiveData.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

    fun getSavedNews() = liveData {
        newsRepository.getSavedNews().collect {
            emit(it)
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveNews(article)
    }

    fun getArticle(url: String) = liveData {
        newsRepository.getArticle(url).collect {
            emit(it)
        }
    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteNews(article)
    }

    //search
    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    fun getSearchedBreakingNews(country: String, query: String, page: Int) = viewModelScope.launch {
        try {
            if (CommonUtil().hasInternetConnection(getApplication())) {
                searchedNews.postValue(Resource.Loading())
                val response = newsRepository.getSearchedNewsHeadlines(country, query, page)
                searchedNews.postValue(response)
            } else {
                searchedNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException ->
                    searchedNews.postValue(Resource.Error("Network failure"))
                else ->
                    searchedNews.postValue(Resource.Error(t.message.toString()))
            }
        }
    }
}