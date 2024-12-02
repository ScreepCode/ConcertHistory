package de.buseslaar.concerthistory.views.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.data.remote.service.SetlistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class SearchViewModel : BaseViewModel() {
    var tabIndex: SearchTab by mutableStateOf(SearchTab.SETLISTS)

    private val artistService = ArtistService()
    private var setlistService = SetlistService()

    private val setlistFavoritesRepository = SetlistRepository()
    private val artistFavoritesRepository = ArtistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = setlistFavoritesRepository.favoriteSetlists
    val favoriteArtists: Flow<List<Artist>> = artistFavoritesRepository.favoriteArtists

    // Artist search
    var artists by mutableStateOf(emptyList<ArtistDto>())
    var artistSearchText by mutableStateOf("")
    var artistErrorMessage: String by mutableStateOf("")

    var artistTextFieldFocused by mutableStateOf(false)

    // Concert search
    var concerts by mutableStateOf(emptyList<SetListDto>())
    var concertSearchText by mutableStateOf("")
    var concertErrorMessage by mutableStateOf("")

    var concertTextFieldFocused by mutableStateOf(false)

    fun initialize(initialTab: SearchTab) {
        tabIndex = initialTab
    }

    fun searchArtist() {
        asyncRequest(onError = { exception ->
            artistErrorMessage = exception.message ?: "Unknown error"
        }) {
            artists = emptyList()
            artistErrorMessage = ""
            artists = artistService.searchArtist(artistSearchText).artists
        }
    }

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

    fun addConcertToFavorites(setListDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.insert(setListDto.reduceToEntity())
        }
    }

    fun removeConcertFromFavorites(setlistDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.getSetlistById(setlistDto.id)?.let {
                setlistFavoritesRepository.delete(it)
            }
        }
    }

    fun addArtistToFavorites(artist: ArtistDto) {
        asyncRequest {
            artistFavoritesRepository.insert(artist.reduceToEntity())
        }
    }

    fun removeArtistFromFavorites(artist: ArtistDto) {
        asyncRequest {
            artistFavoritesRepository.getArtistByMbid(artist.mbid)?.let {
                artistFavoritesRepository.delete(it)
            }
        }
    }
}