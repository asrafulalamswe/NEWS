package com.mdasrafulalam.news.repository

import androidx.lifecycle.LiveData
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.News

class NewsRepository(private val dao:NewsDao)  {
    suspend fun addNews(news: News) = dao.addNews(news)
    suspend fun updateNews(news: News) = dao.updateNews(news)
    fun getAllNews(category: String, country:String) : LiveData<List<News>>  =  dao.getAllNews(category,country)
    fun getBookMarkedNews():LiveData<List<News>> = dao.getBookMarkedNews()
    fun getNewsByCategory(category: String, country: String): LiveData<List<News>> = dao.getNewsByCategory(category, country)
    fun deleteNewsByCategory(category: String) = dao.deleteNewsByCategory(category)
}