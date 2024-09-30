package com.mdasrafulalam.news.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mdasrafulalam.news.viewmodel.NewsViewmodel

private const val TAG = "NewsWorker"

class SyncWorker(context: Context, params: WorkerParameters) : Worker(context, params),
    ViewModelStoreOwner {
    private lateinit var viewModel: NewsViewmodel
    private val appViewModelStore: ViewModelStore by lazy { ViewModelStore() }

    @SuppressLint("WrongThread")
    override fun doWork(): Result {
        return try {
            viewModel =  ViewModelProvider(this)[NewsViewmodel::class.java]
            viewModel.refreshData()
            Log.d(TAG, "doWork: updated")
            makeStatusNotification(
                "There may be some updated news! Please check.",
                applicationContext
            )
            return Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error fetching news")
            throwable.printStackTrace()
            Result.failure()
        }

    }
    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }
}