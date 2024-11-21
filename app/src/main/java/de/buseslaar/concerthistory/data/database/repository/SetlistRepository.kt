package de.buseslaar.concerthistory.data.database.repository

import de.buseslaar.concerthistory.data.database.dao.SetlistDao
import de.buseslaar.concerthistory.data.database.entity.Setlist

class SetlistRepository(private val setlistDao: SetlistDao) {

    val setlists = setlistDao.getAll()

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