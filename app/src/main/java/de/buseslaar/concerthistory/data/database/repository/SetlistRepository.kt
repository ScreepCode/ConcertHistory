package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.FavoritesDatabaseProvider
import de.buseslaar.concerthistory.data.database.entity.Setlist
import kotlinx.coroutines.flow.Flow

class SetlistRepository {
    private val setlistDao = FavoritesDatabaseProvider.getInstance().setlistDao

    val favoriteSetlists: Flow<List<Setlist>> = setlistDao.getAll()

    suspend fun getSetlistById(id: String): Setlist? {
        return setlistDao.getSetlistById(id)
    }

    suspend fun insert(setlist: Setlist) {
        setlistDao.insert(setlist)
    }

    suspend fun delete(setlist: Setlist) {
        setlistDao.delete(setlist)
    }

    suspend fun deleteAll() {
        setlistDao.deleteAll()
    }
}