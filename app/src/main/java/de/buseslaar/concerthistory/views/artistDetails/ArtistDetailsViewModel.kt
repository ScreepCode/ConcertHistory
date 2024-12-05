package de.buseslaar.concerthistory.views.artistDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.database.repository.ArtistRepository
import de.buseslaar.concerthistory.data.database.repository.SetlistRepository
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.service.ArtistService
import de.buseslaar.concerthistory.utils.BaseViewModel
import kotlinx.coroutines.flow.Flow

class ArtistDetailsViewModel : BaseViewModel() {
    var isCached by mutableStateOf(false)

    private val artistService = ArtistService()
    var selectedArtist by mutableStateOf<Any?>(null)
    var lastConcerts by mutableStateOf<List<SetListDto>>(emptyList())
    var isLiked by mutableStateOf(false)

    private val setlistFavoritesRepository = SetlistRepository()
    private val artistFavoritesRepository = ArtistRepository()
    val favoriteSetlists: Flow<List<Setlist>> = setlistFavoritesRepository.favoriteSetlists

    fun initialize(artistMbId: String) {
        asyncRequest {
            artistFavoritesRepository.getArtistByMbid(artistMbId)?.let {
                isCached = true
                selectedArtist = it
                isLiked = it.isFavorite
            } ?: run {
                selectedArtist = artistService.getArtist(artistMbId)
                isLiked = false
            }

            // Case of no connection
            lastConcerts = try {
                artistService.getLastConcerts(artistMbId).setlists
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    fun addConcertToFavorites(setListDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.insert(
                insertSetlist = setListDto, isFavoriteConcert = true, allSetlists = lastConcerts
            )
        }
    }

    fun removeConcertFromFavorites(setlistDto: SetListDto) {
        asyncRequest {
            setlistFavoritesRepository.getSetlistById(setlistDto.id)?.let {
                setlistFavoritesRepository.unfavorite(it)
            }
        }
    }

    fun onLikeToggle() {
        asyncRequest {
            selectedArtist?.let {
                if (isCached) {
                    val artist = it as Artist
                    if (isLiked) {
                        artistFavoritesRepository.unfavorite(artist.mbid)
                    } else {
                        artistFavoritesRepository.updateFavorite(
                            artist.mbid,
                            isFavoriteArtist = true
                        )
                    }
                } else {
                    val artist = it as ArtistDto
                    if (isLiked) {
                        artistFavoritesRepository.unfavorite(artist.mbid)
                    } else {
                        artistFavoritesRepository.updateFavorite(
                            artist.mbid,
                            isFavoriteArtist = true
                        )
                    }
                }

            }
            isLiked = !isLiked
        }
    }
}