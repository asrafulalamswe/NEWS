package com.mdasrafulalam.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mdasrafulalam.news.databinding.FragmentNewsDetailsBinding
import com.mdasrafulalam.news.model.News

class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val newsItem = arguments?.getSerializable("newsItem") as News
        binding.newsItem = newsItem
        binding.shareNewsFAB.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, newsItem.url)
            startActivity(Intent.createChooser(shareIntent, "Share Via"))
        }
        binding.readMoreTV.setOnClickListener {
            val action =
                NewsDetailsFragmentDirections.actionNewsDetailsFragmentToWebViewFragment(newsItem.url)
            findNavController().navigate(action)
        }
    }
}