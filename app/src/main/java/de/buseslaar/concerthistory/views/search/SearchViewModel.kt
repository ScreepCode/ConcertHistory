package de.buseslaar.concerthistory.views.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.data.remote.service.SetlistService
import de.buseslaar.concerthistory.utils.BaseViewModel

class SearchViewModel : BaseViewModel() {
    var tabIndex: Int by mutableStateOf(0)

    private val artistService = ArtistService()


    var artists by mutableStateOf(emptyList<ArtistDto>())
    var artistSearchText by mutableStateOf("")
    var artistErrorMessage: String by mutableStateOf("")

    var artistTextFieldFocused by mutableStateOf(false)

    fun searchArtist() {
        asyncRequest(onError = { exception ->
            artistErrorMessage = exception.message ?: "Unknown error"
        }) {
            artists = emptyList()
            artistErrorMessage = ""
            artists = artistService.searchArtist(artistSearchText).artists
        }
    }

    private var setlistService = SetlistService()

    var concerts by mutableStateOf(emptyList<SetListDto>())
    var concertSearchText by mutableStateOf("")
    var concertErrorMessage by mutableStateOf("")

    var concertTextFieldFocused by mutableStateOf(false)

    fun searchConcerts() {
        asyncRequest(
            onError = { exception ->
                concertErrorMessage = exception.message ?: "Unknown error"
            }
        ) {
            concerts = emptyList()
            concertErrorMessage = ""
            concerts = setlistService.searchSetlist(concertSearchText).setlists
        }
    }
}