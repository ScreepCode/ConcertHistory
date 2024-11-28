package de.buseslaar.concerthistory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import de.buseslaar.concerthistory.data.database.entity.Setlist
import kotlinx.coroutines.flow.Flow

@Dao
interface SetlistDao {

    @Query("SELECT * FROM setlist ORDER BY eventDate ASC")
    fun getAll(): Flow<List<Setlist>>

    @Query("SELECT * FROM setlist WHERE id = :id")
    suspend fun getSetlistById(id: String): Setlist?

    @Insert
    suspend fun insert(setlist: Setlist)

    @Delete
    suspend fun delete(setlist: Setlist)

    @Query("DELETE FROM setlist")
    suspend fun deleteAll()
}