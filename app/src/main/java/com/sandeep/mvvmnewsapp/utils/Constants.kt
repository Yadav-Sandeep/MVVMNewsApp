package com.sandeep.mvvmnewsapp.utils

import com.sandeep.mvvmnewsapp.BuildConfig

/**
 * Created by SandeepY on 27052021
 **/


class Constants {

    companion object {
        const val QUERY_COUNTRY = "country"
        const val QUERY_PAGE = "page"
        const val QUERY_SEARCH = "query"
        const val QUERY_API_KEY = "apiKey"

        const val QUERY_COUNTRY_VALUE = "in"
        const val QUERY_PAGE_VALUE = "1"
        const val QUERY_SEARCH_VALUE = "q"
        const val QUERY_API_KEY_VALUE = BuildConfig.API_KEY
    }
}