package de.buseslaar.concerthistory.views.search.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.utils.BaseViewModel

class ArtistSearchViewModel : BaseViewModel() {

    private val artistService = ArtistService()

    var artists by mutableStateOf(emptyList<ArtistDto>())
    var artistSearchText by mutableStateOf("")

    fun searchArtist() {
        asyncRequest() {
            artists = artistService.searchArtist(artistSearchText).artists

            print(artists)
        }
    }
}