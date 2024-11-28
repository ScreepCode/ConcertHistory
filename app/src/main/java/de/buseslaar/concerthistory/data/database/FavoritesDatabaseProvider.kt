package de.buseslaar.concerthistory.data.database

import android.content.Context
import androidx.room.Room

object FavoritesDatabaseProvider {
    private var instance: FavoritesDatabase? = null

    fun initialize(context: Context) {
        synchronized(this) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favorites"
                ).fallbackToDestructiveMigration().build()
            }
        }
    }

    fun getInstance(): FavoritesDatabase {
        return instance ?: throw IllegalStateException("FavoritesDatabase is not initialized")
    }
}