package de.buseslaar.concerthistory.views.dashboard

import AppContextHolder
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.UserService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class DashboardViewModel : BaseViewModel() {
    private val userService = UserService()
    private val dataStore = DataStoreServiceProvider.getInstance()
    private val artistFavoritesRepository = ArtistRepository()
    val favoriteArtists: Flow<List<Artist>> = artistFavoritesRepository.favoriteArtists

    private var userName: String? = null

    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    var lastAttendedConcerts: MutableList<SetListDto> by mutableStateOf(mutableListOf())

    fun onMenuExpandedChange(expanded: Boolean) {
        _menuExpanded.value = expanded
    }

    fun initialize() {
        asyncRequest {
            userName = dataStore.setlistUsername.first()
            if (!isUserNameProvided()) {
                throw Exception("Username is missing")
            }
            lastAttendedConcerts =
                (userName?.let { userService.getUserAttended(it).setlists }
                    ?: emptyList()).toMutableList()
        }
    }

    fun isUserNameProvided(): Boolean {
        return userName != null && userName!!.isNotBlank()
    }

    // Statistics
    fun getTotalConcertsAttended(): Int {
        return lastAttendedConcerts.size
    }

    fun getTotalUniqueArtists(): Int {
        return lastAttendedConcerts.map { it.artist.name }.distinct().count()
    }

    fun getTotalUniqueLocations(): Int {
        return lastAttendedConcerts.map { it.venue.name }.distinct().count()
    }

    fun isConnected(): Boolean {
        return (AppContextHolder.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}