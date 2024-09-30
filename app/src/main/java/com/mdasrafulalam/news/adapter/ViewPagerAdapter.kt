package com.mdasrafulalam.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mdasrafulalam.news.categoryArray
import com.mdasrafulalam.news.ui.*
import com.mdasrafulalam.news.utils.Constants


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUM_TABS = 7
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                Constants.selectedTab = categoryArray.get(0)
                return AllNewsFragment()
            }
            1 -> {
                Constants.selectedTab = categoryArray.get(1)
                return BusinessFragment()
            }
            2 -> {
                Constants.selectedTab = categoryArray.get(2)
                return EntertainmentFragment()
            }
            3 -> {
                Constants.selectedTab = categoryArray.get(3)
                return ScienceFragment()
            }
            4 -> {
                Constants.selectedTab = categoryArray.get(4)
                return SportsFragment()
            }
            5 -> {
                Constants.selectedTab = categoryArray.get(5)
                return TechnologyFragment()
            }
            else -> {
                Constants.selectedTab = categoryArray.get(6)
                return HealthFragment()
            }
        }
    }
}