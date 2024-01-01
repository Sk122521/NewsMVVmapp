package com.example.newsmvvmapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsmvvmapp.NewsActivity
import com.example.newsmvvmapp.R
import com.example.newsmvvmapp.databinding.FragmentArticleBinding
import com.example.newsmvvmapp.databinding.FragmentBreakingNewsfragmentBinding
import com.example.newsmvvmapp.ui.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {

    private var _binding : FragmentArticleBinding? = null
    private val binding  get()   = _binding!!

    lateinit var viewModel: NewsViewModel
    val args :   ArticleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully", LENGTH_SHORT).show()
        }

    }

    println("Saket lumar is here")
    djhjdhhdhdhh
    ggdggdgdgdgq

}