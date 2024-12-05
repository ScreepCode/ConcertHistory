package de.buseslaar.concerthistory.views.visited

import AppContextHolder
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.datastore.DataStoreServiceProvider
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.UserService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class VisitedViewModel : BaseViewModel() {
    private val userService = UserService()
    private val dataStore = DataStoreServiceProvider.getInstance()
    private val favoritesRepository = SetlistRepository()

    private var userName: String? = null

    var lastAttendedConcerts: MutableList<SetListDto> by mutableStateOf(mutableListOf())
    val favoriteSetlists: Flow<List<Setlist>> = favoritesRepository.favoriteSetlists

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

    fun isConnected(): Boolean {
        return (AppContextHolder.getInstance().getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
    }
}