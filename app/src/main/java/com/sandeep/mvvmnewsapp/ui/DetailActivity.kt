package com.sandeep.mvvmnewsapp.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sandeep.mvvmnewsapp.R
import com.sandeep.mvvmnewsapp.databinding.ActivityDetailBinding
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: NewsViewModel by viewModels()
    private var articleData: Article? = null
    private var showSaveButton: Boolean = false
    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        init()
        setListener()
        loadWebView()
    }

    private fun init(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        articleData = args.newsData
        showSaveButton = args.showSaveButton
        supportActionBar?.title = articleData!!.title;

        if(showSaveButton)
            binding.floatingActionButton.visibility= View.VISIBLE
        else
            binding.floatingActionButton.visibility= View.GONE
    }

    private fun setListener(){
        binding.floatingActionButton.setOnClickListener {
            viewModel.saveArticle(articleData!!)
            Snackbar.make(it, getString(R.string.saved_sucessfully), Snackbar.LENGTH_LONG).show()
            binding.floatingActionButton.visibility = View.GONE
        }
    }

    private fun loadWebView(){
        binding.webview.apply {
            webViewClient = myClient()
            articleData!!.url?.let { loadUrl(it) }
        }
    }

    inner class myClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (binding.webViewProgressBar.isVisible)
                binding.webViewProgressBar.visibility = View.INVISIBLE
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (!binding.webViewProgressBar.isVisible)
                binding.webViewProgressBar.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }


}