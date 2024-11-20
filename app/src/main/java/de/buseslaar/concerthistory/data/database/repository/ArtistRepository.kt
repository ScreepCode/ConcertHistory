package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.dao.ArtistDao
import de.buseslaar.concerthistory.data.database.entity.Artist

class ArtistRepository(private val artistDao: ArtistDao) {
    val artists = artistDao.getAll()

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