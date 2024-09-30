package com.mdasrafulalam.news.network

import com.mdasrafulalam.news.model.NewsModel
import com.mdasrafulalam.news.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET(Constants.END_POINT)
    suspend fun getTopNews(@Query(Constants.PARAM_COUNTRY) q: String, @Query(Constants.PARAM_KEY) apiKey: String): NewsModel

    @GET(Constants.END_POINT)
    suspend fun getCategoryNews(
        @Query(Constants.PARAM_COUNTRY) q: String,
        @Query(Constants.PARAM_CATEGORY) category: String,
        @Query(Constants.PARAM_KEY) apiKey: String
    ): NewsModel
}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}