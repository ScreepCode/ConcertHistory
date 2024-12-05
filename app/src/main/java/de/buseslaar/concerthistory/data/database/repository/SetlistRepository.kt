package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.mapper.reduceToEntity
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import kotlinx.coroutines.flow.Flow

class SetlistRepository {
    private val setlistDao = FavoritesDatabaseProvider.getInstance().setlistDao
    private val artistDao = FavoritesDatabaseProvider.getInstance().artistDao

    val favoriteSetlists: Flow<List<Setlist>> = setlistDao.getAllFavorites()

    suspend fun getSetlistById(id: String): Setlist? {
        return setlistDao.getSetlistById(id)
    }

    suspend fun insert(
        insertSetlist: SetListDto,
        isFavoriteConcert: Boolean = true,
        allSetlists: List<SetListDto>
    ) {
        // Add artist if not already in database
        val existingArtist = artistDao.getArtistById(insertSetlist.artist.mbid)
        if (existingArtist == null) {
            artistDao.insert(insertSetlist.artist.reduceToEntity(isFavorite = false))
        }

        for (setlist in allSetlists) {
            val existingSetlist = setlistDao.getSetlistById(setlist.id)
            if (existingSetlist != null && insertSetlist.id == setlist.id) {
                setlistDao.update(setlist.reduceToEntity(isFavorite = isFavoriteConcert))
            } else if (existingSetlist == null && insertSetlist.id == setlist.id) {
                setlistDao.insert(setlist.reduceToEntity(isFavorite = isFavoriteConcert))
            } else if (existingSetlist == null) {
                setlistDao.insert(setlist.reduceToEntity(isFavorite = false))
            }
        }
    }

    suspend fun unfavorite(setlist: Setlist) {
        setlistDao.update(setlist.copy(isFavorite = false))
        val artist = artistDao.getArtistById(setlist.artistMbid)
        if (artist != null && !artist.isFavorite && setlistDao.hasNoFavoriteConcerts(setlist.artistMbid)) {
            artistDao.delete(artist)
        }
    }

    suspend fun unfavoriteAll() {
        setlistDao.unfavoriteAll()
        setlistDao.getAll().collect { artists ->
            artists.forEach { artist ->
                if (setlistDao.hasNoFavoriteConcerts(artist.artistMbid)) {
                    setlistDao.delete(artist)
                }
            }
        }
    }

    suspend fun delete(setlist: Setlist) {
        setlistDao.delete(setlist)
    }

    suspend fun deleteAll() {
        setlistDao.deleteAll()
    }
}