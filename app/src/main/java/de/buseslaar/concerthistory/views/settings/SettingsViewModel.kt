package de.buseslaar.concerthistory.views.settings

import android.annotation.SuppressLint
import android.app.Application
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import de.buseslaar.concerthistory.dataStore
import de.buseslaar.concerthistory.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SuppressLint("StaticFieldLeak")
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    val dataStore = context.dataStore

    val themeKey = stringPreferencesKey("theme")
    val setlistUsernameKey = stringPreferencesKey("setListUsername")

    val theme: ThemeMode = ThemeMode.valueOf(context.dataStore.data.map { preferences ->
        preferences[themeKey] ?: ThemeMode.SYSTEM.value
    }.toString())

    val setlistUsername: Flow<String> = dataStore.data.map { preferences ->
        preferences[setlistUsernameKey] ?: ""
    }
}

