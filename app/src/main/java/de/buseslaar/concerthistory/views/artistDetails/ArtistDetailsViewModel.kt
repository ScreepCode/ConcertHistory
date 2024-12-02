package de.buseslaar.concerthistory.views.artistDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ArtistDetailsViewModel : BaseViewModel() {

    private val artistService = ArtistService()
    var artist by mutableStateOf<ArtistDto?>(null)
    var lastConcerts by mutableStateOf<List<SetListDto>>(emptyList())
    var isLiked by mutableStateOf<Boolean>(false)

    private val favoritesRepository = SetlistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = favoritesRepository.favoriteSetlists

    fun initialize(artistMbId: String) {
        asyncRequest {
            artist = artistService.getArtist(artistMbId)
            lastConcerts = artistService.getLastConcerts(artistMbId).setlists
        }
    }

    fun addConcertToFavorites(setListDto: SetListDto) {
        asyncRequest {
            favoritesRepository.insert(setListDto.reduceToEntity())
        }
    }

    fun removeConcertFromFavorites(setlistDto: SetListDto) {
        asyncRequest {
            favoritesRepository.getSetlistById(setlistDto.id)?.let {
                favoritesRepository.delete(it)
            }
        }
    }
}