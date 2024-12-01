package de.buseslaar.concerthistory.views.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel : BaseViewModel() {
    var tabIndex: Int by mutableIntStateOf(0)

    private val setlistFavoritesRepository = SetlistRepository()
    private val artistFavoritesRepository = ArtistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = setlistFavoritesRepository.favoriteSetlists
    val favoriteArtists: Flow<List<Artist>> = artistFavoritesRepository.favoriteArtists

    fun initialize() {

    }

    fun removeConcertFromFavorites(setlist: Setlist) {
        asyncRequest {
            setlistFavoritesRepository.delete(setlist)
        }
    }

    fun removeArtistFromFavorites(artist: Artist) {
        asyncRequest {
            artistFavoritesRepository.delete(artist)
        }
    }
}