package com.example.newsmvvmapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsmvvmapp.R
import com.example.newsmvvmapp.databinding.IitemArticlePreviewBinding
import com.example.newsmvvmapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val  binding : IitemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differcallback =  object :  DiffUtil.ItemCallback<Article>(){

       override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           TODO("Not yet implemented")
           return oldItem.url == newItem.url
       }

       override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           TODO("Not yet implemented")
           return oldItem == newItem
       }

   }
    val differ =  AsyncListDiffer(this,differcallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =  IitemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        TODO("Not yet implemented")
        val  article=  differ.currentList[position]

        holder.binding.apply {
            Glide.with(this.root).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            root.setOnClickListener {
//                onItemClickListener?.let{
//                    it(article)
//                    //  article
//                }

            }
        }



        }
    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }

}

