package de.buseslaar.concerthistory.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.buseslaar.concerthistory.data.database.dao.ArtistDao
import de.buseslaar.concerthistory.data.database.dao.SetlistDao
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist

@Database(entities = [Artist::class, Setlist::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val artistDao: ArtistDao
    abstract val setlistDao: SetlistDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null
        fun getInstance(context: Context): FavoritesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoritesDatabase::class.java,
                        "favorites"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}