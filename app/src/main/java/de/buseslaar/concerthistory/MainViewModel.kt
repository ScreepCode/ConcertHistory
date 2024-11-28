package de.buseslaar.concerthistory

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    fun initialize() {
        DataStoreServiceProvider.initialize(application)

        _isReady.value = true
    }
}