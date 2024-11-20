package de.buseslaar.concerthistory.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import de.buseslaar.concerthistory.dataStore
import de.buseslaar.concerthistory.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreService(context: Context) {
    private val dataStore = context.dataStore

    val themeKey = stringPreferencesKey("theme")
    val setlistUsernameKey = stringPreferencesKey("setListUsername")

    val theme: Flow<ThemeMode> = dataStore.data.map { preferences ->
        ThemeMode.valueOf(preferences[themeKey] ?: ThemeMode.SYSTEM.toString())
    }

    val setlistUsername: Flow<String> = dataStore.data.map { preferences ->
        preferences[setlistUsernameKey] ?: ""
    }

    suspend fun setTheme(theme: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.toString()
        }
    }

    suspend fun setSetlistUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[setlistUsernameKey] = username
        }
    }

    fun getDataStore() = dataStore
}