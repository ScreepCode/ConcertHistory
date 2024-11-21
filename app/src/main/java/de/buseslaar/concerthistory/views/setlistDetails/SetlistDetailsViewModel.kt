package de.buseslaar.concerthistory.views.setlistDetails

import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.UserService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class SetlistDetailsViewModel : BaseViewModel() {
    private val userService = UserService()
    private val dataStore = DataStoreServiceProvider.getInstance()
    private var userName: String? = null
    var selectedSetlist: SetListDto? = null

    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    fun initialize(selectedSetlistId: String) {
        asyncRequest {
            userName = dataStore.setlistUsername.first()

            selectedSetlist = (userName?.let { userService.getUserAttended(it).setlists }
                ?: emptyList()).find { it.id == selectedSetlistId }
                ?: throw Exception("Setlist not found")
        }
    }

    fun onMenuExpandedChange(expanded: Boolean) {
        _menuExpanded.value = expanded
    }
}