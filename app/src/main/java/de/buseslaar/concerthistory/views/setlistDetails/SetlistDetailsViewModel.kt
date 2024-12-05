package de.buseslaar.concerthistory.views.setlistDetails

import AppContextHolder
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.data.remote.service.SetlistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SetlistDetailsViewModel : BaseViewModel() {
    private val dataStore = DataStoreServiceProvider.getInstance()
    private val setlistService = SetlistService()
    private val artistService = ArtistService()
    private val favoritesRepository = SetlistRepository()

    var selectedSetlist by mutableStateOf<SetListDto?>(null)
    var isLiked by mutableStateOf(false)

    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    fun initialize(selectedSetlistId: String) {
        asyncRequest {
            selectedSetlist = setlistService.getSetlist(selectedSetlistId)

            isLiked = favoritesRepository.getSetlistById(selectedSetlistId) != null
        }
    }

    fun onMenuExpandedChange(expanded: Boolean) {
        _menuExpanded.value = expanded
    }

    fun onLikeToggle() {
        if (isLiked) {
            removeConcertFromFavorites()
        } else {
            addConcertToFavorites()
        }
        isLiked = !isLiked
    }

    private fun addConcertToFavorites() {
        asyncRequest {
            selectedSetlist?.let { setlist ->
                val lastConcerts = artistService.getLastConcerts(setlist.artist.mbid).setlists
                favoritesRepository.insert(
                    insertSetlist = setlist,
                    isFavoriteConcert = true,
                    allSetlists = lastConcerts
                )
            }
        }
    }

    private fun removeConcertFromFavorites() {
        asyncRequest {
            favoritesRepository.getSetlistById(selectedSetlist!!.id)?.let {
                favoritesRepository.unfavorite(it)
            }
        }
    }

    fun isConnected(): Boolean {
        return (AppContextHolder.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}