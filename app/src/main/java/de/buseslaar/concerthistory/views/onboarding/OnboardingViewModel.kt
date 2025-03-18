package de.buseslaar.concerthistory.views.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import kotlinx.coroutines.launch

class OnboardingViewModel : ViewModel() {
    private val dataStoreService = DataStoreServiceProvider.getInstance()

    fun setOnboardingCompleted(completed: Boolean) {
        viewModelScope.launch {
            dataStoreService.setOnboardingCompleted(completed)
        }
    }

    fun setSetlistUsername(userName: String) {
        viewModelScope.launch {
            dataStoreService.setSetlistUsername(userName)
        }
    }
}