package com.example.newsmvvmapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmvvmapp.NewsActivity
import com.example.newsmvvmapp.R
import com.example.newsmvvmapp.adapter.NewsAdapter
import com.example.newsmvvmapp.databinding.FragmentBreakingNewsfragmentBinding
import com.example.newsmvvmapp.ui.viewmodels.NewsViewModel
import com.example.newsmvvmapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsmvvmapp.util.Resource

/**
 * A simple [Fragment] subclass.
 * Use the [BreakingNewsfragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BreakingNewsfragment : Fragment(R.layout.fragment_breaking_newsfragment) {

    private var _binding : FragmentBreakingNewsfragmentBinding? = null
    private val binding  get()   = _binding!!

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
             val bundle = Bundle().apply {
                 putSerializable("article",it)
             }
            findNavController().navigate(R.id.action_breakingNewsfragment_to_articleFragment,bundle)
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success ->{
                   hideprogressBar()
                    response.data?.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalpages = newsResponse.totalResults / QUERY_PAGE_SIZE +2
                        isLastPage = viewModel.breakingNewsPage == totalpages
                        if (isLastPage){
                            binding.rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideprogressBar()
                    response.message?.let {message ->
                        Log.e("TAG","An error occured: $message ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = false
    }

    private fun hideprogressBar()
    { binding.paginationProgressBar.visibility = View.INVISIBLE
      isLoading = true
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(this@BreakingNewsfragment.scrollListener)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBreakingNewsfragmentBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    var isLoading  = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener  = object  : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutmanager  = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition  = layoutmanager.findFirstVisibleItemPosition()
            val visibleItemCount  = layoutmanager.childCount
            val totalItemCount  = layoutmanager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem  = firstVisibleItemPosition +visibleItemCount >= totalItemCount
            val isnOtAtBeginning  = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =  isNotLoadingAndNotLastPage && isAtLastItem && isnOtAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            if(shouldPaginate){
                viewModel.getBreakingNews("us")
                isScrolling = false
            }




        }
    }


}