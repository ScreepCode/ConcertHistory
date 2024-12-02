package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.database.entity.Artist
import kotlinx.coroutines.flow.Flow

class ArtistRepository() {
    private val artistDao = FavoritesDatabaseProvider.getInstance().artistDao

    val favoriteArtists: Flow<List<Artist>> = artistDao.getAll()

    suspend fun getArtistByMbid(mbid: String): Artist? {
        return artistDao.getArtistById(mbid)
    }

    suspend fun insert(artist: Artist) {
        artistDao.insert(artist)
    }

    suspend fun delete(artist: Artist) {
        artistDao.delete(artist)
    }

    suspend fun deleteAll() {
        artistDao.deleteAll()
    }
}