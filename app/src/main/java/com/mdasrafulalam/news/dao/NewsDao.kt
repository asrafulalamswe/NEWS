package com.mdasrafulalam.news.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mdasrafulalam.news.model.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(news: News)

    @Update
    suspend fun updateNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("select * from tbl_news where category = :category and country = :country")
    fun getAllNews(category: String, country: String): LiveData<List<News>>

    @Query("select * from tbl_news where category = :category and country = :country")
    fun getNewsByCategory(category: String, country: String): LiveData<List<News>>

    @Query("delete from tbl_news where category = :category")
    fun deleteNewsByCategory(category: String)

    @Query("select * from tbl_news where is_bookmared = 1")
    fun getBookMarkedNews(): LiveData<List<News>>
}