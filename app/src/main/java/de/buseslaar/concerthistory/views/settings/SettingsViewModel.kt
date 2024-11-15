package de.buseslaar.concerthistory.views.settings

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.buseslaar.concerthistory.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val dataStore = context.dataStore

    private val themeKey = stringPreferencesKey("theme")
    private val setListUsernameKey = stringPreferencesKey("setListUsername")

    val theme: Flow<String> = dataStore.data.map { preferences ->
        preferences[themeKey] ?: "system"
    }

    val setlistUsername: Flow<String> = dataStore.data.map { preferences ->
        preferences[setListUsernameKey] ?: ""
    }

    fun saveTheme(theme: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[themeKey] = theme
            }
        }
    }

    fun saveSetlistUserName(setListUsername: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[setListUsernameKey] = setListUsername
            }
        }
    }
}

