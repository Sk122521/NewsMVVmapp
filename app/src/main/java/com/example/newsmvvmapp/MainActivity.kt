package com.example.newsmvvmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsmvvmapp.databinding.ActivityNewsBinding
import com.example.newsmvvmapp.db.ArticleDatabase
import com.example.newsmvvmapp.repository.NewsRepository
import com.example.newsmvvmapp.ui.viewmodels.NewsViewModel
import com.example.newsmvvmapp.ui.viewmodels.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {


      private var activityNewsBinding : ActivityNewsBinding ? = null
      private val binding get() = activityNewsBinding!!

      lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        activityNewsBinding = ActivityNewsBinding.inflate(layoutInflater)


        val repository =  NewsRepository(ArticleDatabase(this))

        val viewModelProviderFactory = NewsViewModelProviderFactory(repository)

        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        binding.bottomNavigationView.setupWithNavController(binding.fragmentContainerView.findNavController())


    }
}