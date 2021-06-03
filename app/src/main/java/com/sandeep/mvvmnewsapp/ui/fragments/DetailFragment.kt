package com.sandeep.mvvmnewsapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sandeep.mvvmnewsapp.R
import com.sandeep.mvvmnewsapp.databinding.FragmentDetailBinding
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: NewsViewModel by viewModels()
    private var articleData: Article? = null
    private var showSaveButton: Boolean = false
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleData = args.newsData
        showSaveButton = args.showSaveButton
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_detail, container, false
        )
        binding.webViewProgressBar.visibility = View.VISIBLE
        init()
        setListener()
        loadWebView()
        return binding.root
    }

    private fun init(){
        (requireActivity() as AppCompatActivity).supportActionBar?.title = articleData?.title

        if (showSaveButton)
            binding.floatingActionButton.visibility = View.VISIBLE
        else
            binding.floatingActionButton.visibility = View.GONE
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
}