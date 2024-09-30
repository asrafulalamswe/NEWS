package com.mdasrafulalam.news.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlinx.coroutines.flow.Flow

private const val prefName = "country_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = prefName
)

class DataPreference(context: Context) {
    private val selectedCountry = stringPreferencesKey("selectedCountry")
    private val selectedPosition = longPreferencesKey("selectedPosition")
    private val selectedLayout = stringPreferencesKey("selectedLayout")
    private val darkMode = booleanPreferencesKey("darkMode")

    val selectedCountryFlow: Flow<String> = context.dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[selectedCountry] ?: "no country found"
    }
    val selectedLayoutFlow: Flow<String> = context.dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[selectedLayout] ?: "Linear"
    }

    val selectedPositionFlow: Flow<Long> = context.dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[selectedPosition] ?: 0
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[darkMode] ?: false
    }

    suspend fun setCountry(country: String, position: Long, context: Context) {
        context.dataStore.edit {
            it[selectedCountry] = country
            it[selectedPosition] = position
        }
    }

    suspend fun setLayout(layout: String, context: Context) {
        context.dataStore.edit {
            it[selectedLayout] = layout
        }
    }

    suspend fun setDarkModeValue(darkmode: Boolean, context: Context) {
        context.dataStore.edit {
            it[darkMode] = darkmode
        }
    }
}