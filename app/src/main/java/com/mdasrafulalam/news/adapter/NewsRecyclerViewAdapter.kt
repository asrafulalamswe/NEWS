package com.mdasrafulalam.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.news.databinding.NewsListItemBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.ui.HomeFragmentDirections

class NewsRecyclerViewAdapter(
    val lifecycleOwner: LifecycleOwner,
    val addBookmarkCallback: (News) -> Unit
) :
    ListAdapter<News, NewsRecyclerViewAdapter.NewsViewHolder>(NewsDiffCallback()) {
    class NewsViewHolder(val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsModel: News) {
            binding.newsItem = newsModel
            binding.itemCardView.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToNewsDetailsFragment(newsItem = newsModel)
                it.findNavController().navigate(action)
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.url == newItem.url
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsModel: News = getItem(position)
        holder.bind(newsModel)
        holder.binding.bookmarkIV.setOnClickListener {
            newsModel.isBookmared = !newsModel.isBookmared
            holder.bind(newsModel)
            addBookmarkCallback(newsModel)
        }

    }
}