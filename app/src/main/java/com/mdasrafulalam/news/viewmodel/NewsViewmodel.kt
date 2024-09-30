package com.mdasrafulalam.news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mdasrafulalam.news.db.NewsDB
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.network.NewsApi
import com.mdasrafulalam.news.repository.NewsRepository
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class NewsApiStatus { LOADING, ERROR, DONE }
class NewsViewmodel(application: Application) : AndroidViewModel(application) {
    private var repository: NewsRepository
    private val _status = MutableLiveData<NewsApiStatus>()
    private val _topNewsList = MutableLiveData<List<Article>>()
    private val topNewsList: LiveData<List<Article>> = _topNewsList
    private var _businessNewsList = MutableLiveData<List<Article>>()
    private val businessNewsList: LiveData<List<Article>> = _businessNewsList
    private val _entertainNewsList = MutableLiveData<List<Article>>()
    private val entertainNewsList: LiveData<List<Article>> = _entertainNewsList
    private val _healthNewsList = MutableLiveData<List<Article>>()
    private val healthNewsList: LiveData<List<Article>> = _healthNewsList
    private val _scienceNewsList = MutableLiveData<List<Article>>()
    private val scienceNewsList: LiveData<List<Article>> = _scienceNewsList
    private val _sportsNewsList = MutableLiveData<List<Article>>()
    private val sportsNewsList: LiveData<List<Article>> = _sportsNewsList
    private val _technologyNewsList = MutableLiveData<List<Article>>()
    private val technologyNewsList: LiveData<List<Article>> = _technologyNewsList

    init {
        val dao = NewsDB.getDB(application).getNewsDao()
        repository = NewsRepository(dao)
        refreshData()
    }

    fun refreshAllNews() {
        getTopNews()
    }

    fun refreshBusinessNews() {
        getBusinessNews()
    }

    fun refreshEntertainmentNews() {
        getEntertainmentNews()
    }

    fun refreshHealthNews() {
        getHealthNews()
    }

    fun refreshScienceNews() {
        getScienceNews()
    }

    fun refreshSportsNews() {
        getSportsNews()
    }

    fun refreshTechnologyNews() {
        getTechnologyNews()
    }

    fun refreshBookMarkedNews() {
        getBookMaredNews()
    }

    fun refreshData() {
        getTopNews()
        getBusinessNews()
        getEntertainmentNews()
        getHealthNews()
        getScienceNews()
        getSportsNews()
        getTechnologyNews()
        getBookMaredNews()
    }

    private fun addNews(news: News) {
        viewModelScope.launch {
            repository.addNews(news)
        }
    }

    fun updateBookMark(news: News) {
        viewModelScope.launch {
            repository.updateNews(news)
        }
    }

    fun loadDataIntoDB(
        category: String,
        newsList: MutableLiveData<List<Article>>,
        country: String
    ) {
        for (i in newsList.value!!) {
            var author = i.author
            val content = i.content
            val description = i.description
            val publishedAt = i.publishedAt
            val title = i.title
            val url = i.url
            var urlToImage = i.urlToImage
            if (author == null) {
                author = "Not Found"
            }
            if (urlToImage == null) {
                urlToImage =
                    "https://storage.googleapis.com/afs-prod/media/4acdae97a52b43e4a9aaffb4f78f6eae/3000.webp"
            }

            val news = News(
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                title = title,
                url = url,
                urlToImage = urlToImage,
                category = category,
                country = country,
            )
            addNews(news)
        }
    }

    private fun getTopNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _topNewsList.value = NewsApi.retrofitService.getTopNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                Log.d("list", "list: ${_topNewsList.value!!.size}")
                if (topNewsList.value!!.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_TOP_NEWS,
                            _topNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _topNewsList.value = listOf()
            }
        }
    }

    private fun getBusinessNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _businessNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_BUSINESS,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (businessNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_BUSINESS,
                            _businessNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _businessNewsList.value = listOf()
            }
        }
    }

    private fun getEntertainmentNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _entertainNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_ENTERTAINMENT,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (entertainNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_ENTERTAINMENT,
                            _entertainNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _entertainNewsList.value = listOf()
            }
        }
    }

    private fun getHealthNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _healthNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_HEALTH,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (healthNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_HEALTH,
                            _healthNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _healthNewsList.value = listOf()
            }
        }
    }

    private fun getScienceNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _scienceNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_SCIENCE,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (scienceNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_SCIENCE,
                            _scienceNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _scienceNewsList.value = listOf()
            }
        }
    }

    private fun getSportsNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _sportsNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_SPORTS,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (sportsNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_SPORTS,
                            _sportsNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _sportsNewsList.value = listOf()
            }
        }
    }

    private fun getTechnologyNews() {
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _technologyNewsList.value = NewsApi.retrofitService.getCategoryNews(
                    Constants.COUNTRY.value.toString(),
                    Constants.CATEGORY_TECHNOLOGY,
                    Constants.API_KEY
                ).articles
                _status.value = NewsApiStatus.DONE
                if (technologyNewsList.value!!.size > 0) {
                    viewModelScope.launch(Dispatchers.IO) {
                        loadDataIntoDB(
                            Constants.CATEGORY_TECHNOLOGY,
                            _technologyNewsList,
                            Constants.COUNTRY.value.toString()
                        )
                    }
                }
            } catch (e: Exception) {
                _status.value = NewsApiStatus.ERROR
                _technologyNewsList.value = listOf()
            }
        }
    }

    fun getBookMaredNews(): LiveData<List<News>> = repository.getBookMarkedNews()
    fun getAllNews(country: String): LiveData<List<News>> =
        repository.getAllNews(Constants.CATEGORY_TOP_NEWS, country)


    fun deleteNewsByCategory(category: String) = repository.deleteNewsByCategory(category)
    fun getBusinessNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)
    fun getEntertainmentNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)

    fun getHealthNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)

    fun getScienceNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)

    fun getSportsNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)

    fun getTechnologyNews(category: String, country: String): LiveData<List<News>> =
        repository.getNewsByCategory(category, country)
}