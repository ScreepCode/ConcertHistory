package de.buseslaar.concerthistory.views.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.UserService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class DashboardViewModel : BaseViewModel() {
    private val userService = UserService()
    private val dataStore = DataStoreServiceProvider.getInstance()

    private var userName: String? = null

    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    var lastAttendedConcerts: MutableList<SetListDto> by mutableStateOf(mutableListOf())

    fun onMenuExpandedChange(expanded: Boolean) {
        _menuExpanded.value = expanded
    }

    fun initialize() {
        asyncRequest {
            userName = dataStore.setlistUsername.first()
            if (!isUserNameProvided()) {
                throw Exception("Username is missing")
            }
            lastAttendedConcerts =
                (userName?.let { userService.getUserAttended(it).setlists }
                    ?: emptyList()).toMutableList()
        }
    }

    fun isUserNameProvided(): Boolean {
        return userName != null && userName!!.isNotBlank()
    }
}