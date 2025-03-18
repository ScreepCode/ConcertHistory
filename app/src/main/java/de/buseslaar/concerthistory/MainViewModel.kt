package de.buseslaar.concerthistory

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()
    private val _isOnboardingCompleted = MutableStateFlow(false)
    val isOnboardingCompleted = _isOnboardingCompleted.asStateFlow()

    fun initialize() {
        DataStoreServiceProvider.initialize(application)
        FavoritesDatabaseProvider.initialize(application)

        viewModelScope.launch {
            val dataStoreService = DataStoreServiceProvider.getInstance()
            viewModelScope.launch {
                dataStoreService.isOnboardingCompleted.collect { completed ->
                    _isOnboardingCompleted.value = completed
                }
            }
            _isReady.value = true
        }
    }
}