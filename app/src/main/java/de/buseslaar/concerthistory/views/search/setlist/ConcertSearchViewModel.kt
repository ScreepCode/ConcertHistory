package de.buseslaar.concerthistory.views.search.setlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.SetlistService
import de.buseslaar.concerthistory.utils.BaseViewModel

class ConcertSearchViewModel : BaseViewModel() {
    private var setlistService = SetlistService()

    var concerts by mutableStateOf(emptyList<SetListDto>())
    var concertSearchText by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    fun searchConcerts() {
        asyncRequest() {
            concerts = setlistService.searchSetlist(concertSearchText).setlists
        }
    }
}