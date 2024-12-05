package de.buseslaar.concerthistory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.buseslaar.concerthistory.data.database.entity.Setlist
import kotlinx.coroutines.flow.Flow

@Dao
interface SetlistDao {

    @Query("SELECT * FROM setlist ORDER BY eventDate ASC")
    fun getAll(): Flow<List<Setlist>>

    @Query("SELECT * FROM setlist WHERE isFavorite = 1 ORDER BY eventDate ASC")
    fun getAllFavorites(): Flow<List<Setlist>>

    @Query("SELECT * FROM setlist WHERE setlistId = :id")
    suspend fun getSetlistById(id: String): Setlist?

    @Insert
    suspend fun insert(setlist: Setlist)

    @Update
    suspend fun update(setlist: Setlist)

    @Query("UPDATE setlist SET isFavorite = 0")
    suspend fun unfavoriteAll()

    @Delete
    suspend fun delete(setlist: Setlist)

    @Query("DELETE FROM setlist")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) = 0 FROM setlist WHERE artistMbid = :artistId AND isFavorite = 1")
    suspend fun hasNoFavoriteConcerts(artistId: String): Boolean
}