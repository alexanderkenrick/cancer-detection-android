package com.dicoding.asclepius.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.response.ArticlesItem
import com.dicoding.asclepius.response.NewsResponse
import com.dicoding.asclepius.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(application: Application) : ViewModel() {
    private val _listNews = MutableLiveData<List<ArticlesItem>>()
    val listNews: LiveData<List<ArticlesItem>> = _listNews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "NewsViewModel"
        private const val KEY = BuildConfig.API_KEY
    }

    init {
        findNews()
    }

    private fun findNews() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNews("cancer", "health", "en", KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isLoading.value = false
                Log.e("Response", response.raw().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listNews.value = response.body()?.articles
                    }
                } else {
                    Log.e(TAG, "onFailureAtas: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureBawah: ${t.message.toString()}")
            }
        })
    }
}