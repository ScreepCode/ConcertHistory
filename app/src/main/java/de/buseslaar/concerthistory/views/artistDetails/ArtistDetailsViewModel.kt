package de.buseslaar.concerthistory.views.artistDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.utils.BaseViewModel

class ArtistDetailsViewModel : BaseViewModel() {

    private val artistService = ArtistService()
    var artist by mutableStateOf<ArtistDto?>(null)
    var lastConcerts by mutableStateOf<List<SetListDto>>(emptyList())

    fun initialize(artistMbId: String) {
        asyncRequest {
            artist = artistService.getArtist(artistMbId)
            lastConcerts = artistService.getLastConcerts(artistMbId).setlists
        }

    }
}