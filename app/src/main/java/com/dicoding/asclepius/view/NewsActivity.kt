package com.dicoding.asclepius.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityNewsBinding
import com.dicoding.asclepius.response.ArticlesItem
import com.dicoding.asclepius.viewModel.NewsViewModel
import com.dicoding.asclepius.viewModel.ViewModelFactory

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private val newsViewModel by viewModels<NewsViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsViewModel.listNews.observe(this) { listNews ->
            setNews(listNews)
        }

        newsViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvNews.addItemDecoration(itemDecoration)
    }

    private fun setNews(listNews: List<ArticlesItem>) {
        val adapter = NewsAdapter()
        adapter.submitList(listNews)
        if (listNews.isEmpty()) {
            with(binding.txtNotExist) {
                visibility = View.VISIBLE
            }
        } else {
            binding.txtNotExist.visibility = View.GONE
        }
        binding.rvNews.adapter = adapter
        Log.d("ListNews", listNews.size.toString())
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}