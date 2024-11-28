package de.buseslaar.concerthistory.views.favorites

import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel : BaseViewModel() {
    private val setlistFavoritesRepository = SetlistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = setlistFavoritesRepository.favoriteSetlists

    fun initialize() {

    }

    fun removeConcertFromFavorites(setlist: Setlist) {
        asyncRequest {
            setlistFavoritesRepository.delete(setlist)
        }
    }
}