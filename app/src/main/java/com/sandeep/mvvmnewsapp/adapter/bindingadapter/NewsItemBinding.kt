package com.sandeep.mvvmnewsapp.adapter.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.sandeep.mvvmnewsapp.R

/**
 * Created by SandeepY on 27052021
 **/


class NewsItemBinding {
    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
            if (imageUrl != null) {
                imageView.load(imageUrl) {
                    crossfade(600)
                    placeholder(R.drawable.image_placeholder)
                }
            }
        }
    }
}