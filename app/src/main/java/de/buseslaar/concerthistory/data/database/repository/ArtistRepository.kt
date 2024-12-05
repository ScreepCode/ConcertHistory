package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import kotlinx.coroutines.flow.Flow

class ArtistRepository() {
    private val artistDao = FavoritesDatabaseProvider.getInstance().artistDao
    private val setlistDao = FavoritesDatabaseProvider.getInstance().setlistDao

    val savedArtists: Flow<List<Artist>> = artistDao.getAll()
    val favoriteArtists: Flow<List<Artist>> = artistDao.getAllFavorites()

    suspend fun getArtistByMbid(mbid: String): Artist? {
        return artistDao.getArtistById(mbid)
    }

    suspend fun insert(artist: ArtistDto, isFavoriteArtist: Boolean = true) {
        val artistEntity = artist.reduceToEntity(isFavorite = isFavoriteArtist)
        artistDao.insert(artistEntity)
    }

    suspend fun updateFavorite(artistDto: ArtistDto, isFavoriteArtist: Boolean) {
        val existingArtist = artistDao.getArtistById(artistDto.mbid)
        existingArtist?.let {
            val updatedArtistEntity = existingArtist.copy(isFavorite = isFavoriteArtist)
            artistDao.update(updatedArtistEntity)
        }
    }

    suspend fun insertWithSetlists(
        artistId: String,
        isFavoriteArtist: Boolean = true,
        setlists: List<SetListDto>
    ) {
        // Add artist if not already in database
        val existingArtist = artistDao.getArtistById(artistId)
        if (existingArtist == null) {
            artistDao.insert(setlists.first().artist.reduceToEntity(isFavorite = isFavoriteArtist))
            for (setlist in setlists) {
                setlistDao.insert(setlist.reduceToEntity(isFavorite = false))
            }
        } else {
            for (setlist in setlists) {
                val existingSetlist = setlistDao.getSetlistById(setlist.id)
                if (existingSetlist == null) {
                    setlistDao.insert(setlist.reduceToEntity(isFavorite = false))
                }
            }
        }
    }

    suspend fun delete(artist: Artist) {
        artistDao.delete(artist)
    }

    suspend fun unfavorite(artist: Artist) {
        artistDao.update(artist.copy(isFavorite = false))
        if (artistDao.hasNoFavoriteSetlists(artist.mbid)) {
            artistDao.delete(artist)
        }
    }

    suspend fun unfavoriteAll() {
        artistDao.unfavoriteAll()
        artistDao.getAll().collect { artists ->
            artists.forEach { artist ->
                if (artistDao.hasNoFavoriteSetlists(artist.mbid)) {
                    artistDao.delete(artist)
                }
            }
        }
    }
}