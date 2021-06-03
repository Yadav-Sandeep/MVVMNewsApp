package com.sandeep.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandeep.mvvmnewsapp.R
import com.sandeep.mvvmnewsapp.adapter.NewsAdapter
import com.sandeep.mvvmnewsapp.adapter.NewsAdapterNew
import com.sandeep.mvvmnewsapp.databinding.FragmentNewsBinding
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.utils.Resource
import com.sandeep.mvvmnewsapp.utils.observeOnce
import com.sandeep.mvvmnewsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter by lazy { NewsAdapter() }

    //private val newsAdapterNew by lazy { NewsAdapterNew() }
    var job: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        init()
        setRecyclerView()
        requestAPIData()
        return binding.root
    }

    private fun init() {
        binding.newsViewModel = viewModel
        binding.lifecycleOwner = this
        binding.srvNews.layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)
    }

    private fun setRecyclerView() {
        binding.srvNews.layoutManager = LinearLayoutManager(context)
        binding.srvNews.adapter = newsAdapter
        //binding.srvNews.adapter =newsAdapterNew
        showShimmerEffect()

        newsAdapter.setOnItemClickListener {
            listItemClicked(it)
        }
        /*newsAdapterNew.setOnItemClickListener {
            listItemClicked(it)
        }*/
    }

    private fun showShimmerEffect() {
        binding.srvNews.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.srvNews.hideShimmer()
    }

    private fun requestAPIData() {
        viewModel.getBreakingNews()
        viewModel.breakingNewsData.observe(viewLifecycleOwner, Observer { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    newsResponse.data?.let {
                        newsAdapter.setData(it)
                        //newsAdapterNew.differ.submitList(it.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(context, newsResponse.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun requestSearchAPIData(queryText: String?) {
        viewModel.getSearchedBreakingNews("in", queryText.toString(), 1)
        viewModel.searchedNews.observe(this, Observer { newsResponse ->
            when (newsResponse) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    newsResponse.data?.let {
                        newsAdapter.setData(it)
                        //newsAdapterNew.differ.submitList(it.articles.toList())
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(context, newsResponse.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun listItemClicked(article: Article) {
        loadWebView(article)
    }

    private fun loadWebView(article: Article) {
        viewModel.getArticle(article.url!!).observeOnce(viewLifecycleOwner, Observer { articleDB ->
            if (articleDB != null) {
                // for fragment navigation
                /*val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(
                    articleDB, false)
                findNavController().navigate(action)*/

                // for activity navigation
                val action = NewsFragmentDirections.actionNewsFragmentToDetailActivity(
                    articleDB, false)
                findNavController().navigate(action)
            } else {
                // for fragment navigation
                /*val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(
                        article, true)
                findNavController().navigate(action)*/

                // for activity navigation
                val action = NewsFragmentDirections.actionNewsFragmentToDetailActivity(
                    article, true)
                findNavController().navigate(action)
            }
        })
    }

    override fun onQueryTextSubmit(queryText: String?): Boolean {
        if (queryText != null) {
            requestSearchAPIData(queryText)
        }
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        job?.cancel()
        job = MainScope().launch {
            delay(700)
            requestSearchAPIData(p0.toString())
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.searchNews)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.savedNews -> {
                val action =
                    NewsFragmentDirections.actionNewsFragmentToSavedNewsFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }
}