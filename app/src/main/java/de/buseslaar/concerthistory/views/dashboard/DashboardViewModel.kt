package de.buseslaar.concerthistory.views.dashboard

import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : BaseViewModel() {
    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    fun onMenuExpandedChange(expanded: Boolean) {
        _menuExpanded.value = expanded
    }
}