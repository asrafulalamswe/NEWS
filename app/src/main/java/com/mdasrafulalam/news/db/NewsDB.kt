package com.mdasrafulalam.news.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdasrafulalam.news.dao.NewsDao
import com.mdasrafulalam.news.model.News

@Database(entities = [News::class], version = 1)
abstract class NewsDB : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao

    companion object {
        private var db: NewsDB? = null
        fun getDB(context: Context): NewsDB {
            if (db == null) {
                db = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDB::class.java,
                    "news_db"
                ).build()
                return db!!
            }
            return db!!
        }
    }
}