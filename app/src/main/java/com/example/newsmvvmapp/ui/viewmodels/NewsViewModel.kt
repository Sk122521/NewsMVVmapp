package com.example.newsmvvmapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsmvvmapp.models.Article
import com.example.newsmvvmapp.models.NewsResponse
import com.example.newsmvvmapp.repository.NewsRepository
import com.example.newsmvvmapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (val newsrespository : NewsRepository): ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse : NewsResponse ? =  null

    val searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse : NewsResponse ?= null


    init {
        getBreakingNews("us")
    }


    fun getBreakingNews(countryCode : String) = viewModelScope.launch {

        breakingNews.postValue(Resource.Loading())
        val response = newsrespository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun getSearchNews(searchQuery : String)  = viewModelScope.launch {

        searchNews.postValue(Resource.Loading())
        val response = newsrespository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))

    }

    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = it
                }else{
                     val oldArticles = breakingNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let {
               searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = it
                }else{
                    val oldArticles = searchNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: it)
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsrespository.upsert(article)
    }

    fun getSavedNews() = newsrespository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsrespository.deleteArticles(article)
    }


}