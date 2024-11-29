package de.buseslaar.concerthistory.views.setlistDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.UserService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class SetlistDetailsViewModel : BaseViewModel() {
    private val userService = UserService()
    private val dataStore = DataStoreServiceProvider.getInstance()
    private val favoritesRepository = SetlistRepository()

    private var userName: String? = null
    var selectedSetlist by mutableStateOf<SetListDto?>(null)
    var isLiked by mutableStateOf(false)

    private val _menuExpanded = MutableStateFlow(false)
    val menuExpanded: StateFlow<Boolean> = _menuExpanded.asStateFlow()

    fun initialize(selectedSetlistId: String) {
        asyncRequest {
            userName = dataStore.setlistUsername.first()

            selectedSetlist = (userName?.let { userService.getUserAttended(it).setlists }
                ?: emptyList()).find { it.id == selectedSetlistId }
                ?: throw Exception("Setlist not found")

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
            favoritesRepository.insert(selectedSetlist!!.reduceToEntity())
        }
    }

    private fun removeConcertFromFavorites() {
        asyncRequest {
            favoritesRepository.getSetlistById(selectedSetlist!!.id)?.let {
                favoritesRepository.delete(it)
            }
        }
    }
}