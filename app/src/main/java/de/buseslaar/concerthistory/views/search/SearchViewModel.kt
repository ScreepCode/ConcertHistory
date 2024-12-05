package de.buseslaar.concerthistory.views.search

import AppContextHolder
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.R
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.data.remote.service.SetlistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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
    var artistErrorMessage: Int? by mutableStateOf(null)
    var artistTextFieldFocused by mutableStateOf(false)

    // Concert search
    var concerts by mutableStateOf(emptyList<SetListDto>())
    var concertSearchText by mutableStateOf("")
    var concertErrorMessage by mutableStateOf<Int?>(null)
    private var concertErrorCode: Int? by mutableStateOf<Int?>(null)
    var concertTextFieldFocused by mutableStateOf(false)

    fun initialize(initialTab: SearchTab) {
        tabIndex = initialTab

        if (!isConnected()) {
            concertErrorMessage = R.string.no_internet_title
            artistErrorMessage = R.string.no_internet_title
        }
    }

    fun searchArtist() {
        asyncRequest(onError = { exception ->
            artistErrorMessage = parseErrorCode(getErrorCode(exception.message ?: ""))
        }) {
            artists = emptyList()
            artistErrorMessage = null
            if (isConnected()) {
                artists = artistService.searchArtist(artistSearchText).artists
            } else {
                artistErrorMessage = parseErrorCode(408)
            }
        }
    }

    fun searchConcerts() {
        asyncRequest(
            onError = { exception ->
                concertErrorMessage = parseErrorCode(getErrorCode(exception.message ?: ""))
            }
        ) {
            concerts = emptyList()
            concertErrorMessage = null
            concertErrorCode = null
            if (isConnected()) {
                concerts = setlistService.searchSetlist(concertSearchText).setlists
            } else {
                concertErrorMessage = parseErrorCode(408)
            }


        }
    }

    fun addConcertToFavorites(setListDto: SetListDto) {
        asyncRequest {
            val lastConcerts = artistService.getLastConcerts(setListDto.artist.mbid).setlists
            setlistFavoritesRepository.insert(setListDto, isFavoriteConcert = true, lastConcerts)
        }
    }

    fun removeConcertFromFavorites(setlistDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.getSetlistById(setlistDto.id)?.let {
                setlistFavoritesRepository.unfavorite(it)
            }
        }
    }

    fun addArtistToFavorites(artist: ArtistDto) {
        asyncRequest {
            // Add artist to database
            val lastConcerts = artistService.getLastConcerts(artist.mbid).setlists
            artistFavoritesRepository.insertWithSetlists(
                artistId = artist.mbid,
                isFavoriteArtist = true,
                setlists = lastConcerts,
            )
        }
    }

    fun removeArtistFromFavorites(artist: ArtistDto) {
        asyncRequest {
            artistFavoritesRepository.getArtistByMbid(artist.mbid)?.let {
                artistFavoritesRepository.unfavorite(it.mbid)
            }
        }
    }

    private fun getErrorCode(json: String): Int {
        return try {
            Json.decodeFromString<JsonObject>(json).jsonObject["code"]?.jsonPrimitive?.int
                ?: 0
        } catch (_: Exception) {
            0
        }
    }

    private fun isConnected(): Boolean {
        return (AppContextHolder.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null
    }

    private fun parseErrorCode(code: Int): Int {
        return when (code) {
            404 -> R.string.search_no_results_found
            408 -> R.string.no_internet_title
            500 -> R.string.search_internal_server_error
            else -> R.string.search_unknown_error
        }
    }
}