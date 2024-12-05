package de.buseslaar.concerthistory.views.artistDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ArtistDetailsViewModel : BaseViewModel() {

    private val artistService = ArtistService()
    var selectedArtist by mutableStateOf<ArtistDto?>(null)
    var lastConcerts by mutableStateOf<List<SetListDto>>(emptyList())
    var isLiked by mutableStateOf(false)

    private val setlistFavoritesRepository = SetlistRepository()
    private val artistFavoritesRepository = ArtistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = setlistFavoritesRepository.favoriteSetlists

    fun initialize(artistMbId: String) {
        asyncRequest {
            selectedArtist = artistService.getArtist(artistMbId)
            lastConcerts = artistService.getLastConcerts(artistMbId).setlists

            isLiked = artistFavoritesRepository.getArtistByMbid(artistMbId)?.isFavorite == true
        }
    }

    fun addConcertToFavorites(setListDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.insert(setListDto, isFavoriteConcert = true)
        }
    }

    fun removeConcertFromFavorites(setlistDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.getSetlistById(setlistDto.id)?.let {
                setlistFavoritesRepository.unfavorite(it)
            }
        }
    }

    private fun addArtistToFavorites(artist: ArtistDto) {
        asyncRequest {
            artistFavoritesRepository.insertOrUpdateFavorite(artist, isFavoriteArtist = true)
        }
    }

    private fun removeArtistFromFavorites(artist: ArtistDto) {
        asyncRequest {
            artistFavoritesRepository.getArtistByMbid(artist.mbid)?.let {
                artistFavoritesRepository.unfavorite(it)
            }
        }
    }

    fun onLikeToggle() {
        selectedArtist?.let {
            if (isLiked) {
                removeArtistFromFavorites(it)
            } else {
                addArtistToFavorites(it)
            }
        }
        isLiked = !isLiked
    }
}