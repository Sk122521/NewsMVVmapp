package com.example.newsmvvmapp.repository

import com.example.newsmvvmapp.api.RetrofitInstance
import com.example.newsmvvmapp.db.ArticleDatabase
import com.example.newsmvvmapp.models.Article
import retrofit2.Retrofit

class NewsRepository(
    val db : ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode : String , pageNumber : Int )=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)

    suspend fun searchNews(searchQuery : String , pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article)  =
        db.getArticleDao().upsert(article)

    fun getSavedNews()=
        db.getArticleDao().getAllArticles()

    suspend fun deleteArticles(article: Article) =
        db.getArticleDao().deleteArticle(article)



}