package de.buseslaar.concerthistory.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.buseslaar.concerthistory.data.database.dao.ArtistDao
import de.buseslaar.concerthistory.data.database.dao.SetlistDao
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist

@Database(entities = [Artist::class, Setlist::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val artistDao: ArtistDao
    abstract val setlistDao: SetlistDao
}