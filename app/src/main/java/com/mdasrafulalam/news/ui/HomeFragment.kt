package com.mdasrafulalam.news.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.news.viewmodel.NewsViewmodel
import com.mdasrafulalam.news.adapter.ViewPagerAdapter
import com.mdasrafulalam.news.databinding.FragmentHomeBinding
import com.mdasrafulalam.news.utils.Constants

class HomeFragment : Fragment() {

    val viewModel: NewsViewmodel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPage = binding.viewPager
        val tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = Constants.categoryArray[position]
        }.attach()
    }
}