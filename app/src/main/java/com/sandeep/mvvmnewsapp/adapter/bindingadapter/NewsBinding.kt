package com.sandeep.mvvmnewsapp.adapter.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sandeep.mvvmnewsapp.models.NewsResponse
import com.sandeep.mvvmnewsapp.utils.Resource

/**
 * Created by SandeepY on 27052021
 **/


class NewsBinding {

    companion object {

        @BindingAdapter("readAPIResponse", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: Resource<NewsResponse>?
        ) {
            if (apiResponse is Resource.Error) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is Resource.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if (apiResponse is Resource.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }


        @BindingAdapter("readAPIResponse2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: Resource<NewsResponse>?
        ) {
            if (apiResponse is Resource.Error) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is Resource.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiResponse is Resource.Success) {
                textView.visibility = View.INVISIBLE
            }
        }
    }
}