package de.buseslaar.concerthistory.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.Setlist

@Database(entities = [Artist::class, Setlist::class], version = 1, exportSchema = false)
abstract class FavouritesDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: FavouritesDatabase? = null
        fun getInstance(context: Context): FavouritesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavouritesDatabase::class.java,
                        "favourites"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}