package com.mdasrafulalam.news.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_news")
class News(
    val author: String?,
    val content: String?,
    val description: String?,
    @ColumnInfo(name = "date_time")
    val publishedAt: String?,
    val title: String?,
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "image_url")
    val urlToImage: String?,
    val category: String?,
    @ColumnInfo(name = "is_bookmared")
    var isBookmared: Boolean = false,
    var country: String = "us"
) : java.io.Serializable