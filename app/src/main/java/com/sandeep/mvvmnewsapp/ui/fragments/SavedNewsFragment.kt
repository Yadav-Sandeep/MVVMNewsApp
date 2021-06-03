package com.sandeep.mvvmnewsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sandeep.mvvmnewsapp.R
import com.sandeep.mvvmnewsapp.adapter.NewsAdapter
import com.sandeep.mvvmnewsapp.databinding.FragmentSavedNewsBinding
import com.sandeep.mvvmnewsapp.models.Article
import com.sandeep.mvvmnewsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter by lazy { NewsAdapter() }

    //private val newsAdapterNew by lazy { NewsAdapterNew() }
    private var articleListDelete: List<Article>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_saved_news, container, false
        )
        init()
        setRecyclerView()
        setSwipeListener()
        readSavedNews()
        return binding.root
    }

    private fun init() {
        binding.newsViewModel = viewModel
        binding.lifecycleOwner = this
        binding.srvSavedNews.layoutManager = LinearLayoutManager(context)
    }

    private fun setRecyclerView() {
        binding.srvSavedNews.layoutManager = LinearLayoutManager(context)
        binding.srvSavedNews.adapter = newsAdapter
        //binding.srvSavedNews.adapter =newsAdapterNew

        /*newsAdapterNew.setOnItemClickListener {
            listItemClicked(it)
        }*/
        newsAdapter.setOnItemClickListener {
            listItemClicked(it)
        }
    }

    private fun listItemClicked(article: Article) {
        loadWebView(article)
    }

    private fun loadWebView(articlePass: Article) {
        val action =
            SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailFragment(
                articlePass,
                false
            )
        findNavController().navigate(action)
    }

    private fun readSavedNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articleList ->
            articleListDelete = articleList
            newsAdapter.setSavedData(articleList)
            //newsAdapterNew.differ.submitList(articleList)
            if (articleList.isEmpty()) {
                binding.ivSavedNetworkError.visibility = View.VISIBLE
                binding.tvSavedNetworkError.visibility = View.VISIBLE
                binding.tvSavedNetworkError.text = getString(R.string.no_news_saved)
            } else {
                binding.ivSavedNetworkError.visibility = View.GONE
                binding.tvSavedNetworkError.visibility = View.GONE
            }
        })
    }

    private fun setSwipeListener() {
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = articleListDelete?.get(position)
                if (article != null) {
                    viewModel.deleteNews(article)
                    Snackbar.make(binding.root, getString(R.string.delete_sucessfull), Snackbar.LENGTH_LONG)
                        .apply {
                            setAction(getString(R.string.undo)) {
                                viewModel.saveArticle(article)
                            }
                            show()
                        }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.srvSavedNews)
        }
    }
}