package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import kotlinx.coroutines.flow.Flow

class ArtistRepository() {
    private val artistDao = FavoritesDatabaseProvider.getInstance().artistDao
    val savedArtists: Flow<List<Artist>> = artistDao.getAll()
    val favoriteArtists: Flow<List<Artist>> = artistDao.getAllFavorites()

    suspend fun getArtistByMbid(mbid: String): Artist? {
        return artistDao.getArtistById(mbid)
    }

    suspend fun insert(artist: ArtistDto, isFavoriteArtist: Boolean = true) {
        val artistEntity = artist.reduceToEntity(isFavorite = isFavoriteArtist)
        artistDao.insert(artistEntity)
    }

    suspend fun insertOrUpdateFavorite(artist: ArtistDto, isFavoriteArtist: Boolean) {
        val existingArtist = artistDao.getArtistById(artist.mbid)
        if (existingArtist == null) {
            val artistEntity = artist.reduceToEntity(isFavorite = isFavoriteArtist)
            artistDao.insert(artistEntity)
        } else {
            val updatedArtist = existingArtist.copy(isFavorite = isFavoriteArtist)
            artistDao.update(updatedArtist)
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