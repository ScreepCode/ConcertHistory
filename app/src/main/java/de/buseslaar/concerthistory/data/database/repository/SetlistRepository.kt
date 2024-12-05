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

//    suspend fun insert(setlist: SetListDto, isFavoriteConcert: Boolean = true) {
//        val artistEntity = setlist.artist.reduceToEntity(isFavorite = false)
//        artistDao.insert(artistEntity)
//
//        val setlistEntity = setlist.reduceToEntity(isFavorite = isFavoriteConcert)
//        setlistDao.insert(setlistEntity)
//    }

    suspend fun insert(setlist: SetListDto, isFavoriteConcert: Boolean = true) {
        // Add artist if not already in database
        val existingArtist = artistDao.getArtistById(setlist.artist.mbid)
        if (existingArtist == null) {
            artistDao.insert(setlist.artist.reduceToEntity(isFavorite = false))
        }

        val setlistEntity = setlist.reduceToEntity(isFavorite = isFavoriteConcert)
        val existingSetlist = setlistDao.getSetlistById(setlistEntity.setlistId)
        if (existingSetlist == null) {
            setlistDao.insert(setlistEntity)
        } else {
            setlistDao.update(setlistEntity)
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