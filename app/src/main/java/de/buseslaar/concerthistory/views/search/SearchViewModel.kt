package de.buseslaar.concerthistory.views.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.utils.BaseViewModel

class SearchViewModel : BaseViewModel() {
    var tabIndex: Int by mutableStateOf(0)
}