package de.buseslaar.concerthistory.views.settings

import android.annotation.SuppressLint
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.ui.theme.ThemeMode
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

@SuppressLint("StaticFieldLeak")
class SettingsViewModel() : BaseViewModel() {
    private val dataStoreService = DataStoreServiceProvider.getInstance()

    val theme: Flow<ThemeMode> = dataStoreService.theme
    val setlistUsername: Flow<String> = dataStoreService.setlistUsername

    val dataStore = dataStoreService.getDataStore()
    val themeKey = dataStoreService.themeKey
    val setlistUsernameKey = dataStoreService.setlistUsernameKey
}

