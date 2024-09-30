package com.mdasrafulalam.news.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData

class Constants {
    companion object {

        val countryList = listOf(
            "United States of America",
            "Argentina",
            " Ashmore and Cartier Islands",
            "Austria",
            "Belgium",
            "Bulgeria",
            "Brazil",
            "Canada",
            "China",
            "Comoros",
            "Colombia",
            "Cuba",
            "Czech Republic",
            "Germany",
            "Egypt",
            "France",
            "Great Britain",
            "Greece",
            "Hong Kong",
            "Hungary",
            "Indonesia",
            "Ireland",
            "Israel",
            "India",
            "Italy",
            "Japan",
            "Korea",
            "Lithuania",
            "Latvia",
            "Madagascar",
            "Mexico",
            "Malaysia",
            "Niger",
            "Netherlands",
            "Norway",
            "New Zealand",
            "Philippines",
            "Poland",
            "Portugal",
            "Romania",
            "Russia",
            "Russian Federation",
            "Saudi Arabia",
            "Seychelles",
            "Singapore",
            "Slovenia",
            "Slovakia",
            "Thailand",
            "Turkey",
            "Taiwan",
            "Ukraine",
            "UAE",
            "Venezuela",
            "Zambia"
        )
        val countryCode = listOf(
            "us",
            "ar",
            "at",
            "au",
            "be",
            "bg",
            "br",
            "ca",
            "ch",
            "cn",
            "co",
            "cu",
            "cz",
            "de",
            "eg",
            "fr",
            "gb",
            "gr",
            "hk",
            "hu",
            "id",
            "ie",
            "il",
            "in",
            "it",
            "jp",
            "kr",
            "lt",
            "lv",
            "ma",
            "mx",
            "my",
            "ng",
            "nl",
            "no",
            "nz",
            "ph",
            "pl",
            "pt",
            "ro",
            "rs",
            "ru",
            "sa",
            "se",
            "sg",
            "si",
            "sk",
            "th",
            "tr",
            "tw",
            "ua",
            "ae",
            "ve",
            "za"
        )
        const val API_KEY = "6e1f4dc0c07f41318d981fee6210bef2"
        const val END_POINT = "top-headlines"
        const val PARAM_COUNTRY = "country"
        const val PARAM_CATEGORY = "category"
        const val PARAM_KEY = "apiKey"
        var selectedTab = ""
        val categoryArray = arrayOf(
            "Top News",
            "Business",
            "Entertainment",
            "Science",
            "Sports",
            "Technology",
            "Health"
        )

        // Name of Notification Channel for verbose notifications of background work
        @JvmField
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "News Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"
        @JvmField
        val NOTIFICATION_TITLE: CharSequence = "News Updated"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        var COUNTRY = MutableLiveData<String>()
        var ISLINEARLYOUT = MutableLiveData<Boolean>()
        var DARKMODE = MutableLiveData<Boolean>()
        const val CATEGORY_BUSINESS = "business"
        const val CATEGORY_TOP_NEWS = "top_news"
        const val CATEGORY_ENTERTAINMENT = "entertainment"
        const val CATEGORY_HEALTH = "health"
        const val CATEGORY_SCIENCE = "science"
        const val CATEGORY_SPORTS = "sports"
        const val CATEGORY_TECHNOLOGY = "technology"

        @SuppressLint("MissingPermission")
        fun verifyAvailableNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

}