package de.buseslaar.concerthistory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import de.buseslaar.concerthistory.data.database.entity.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist ORDER BY name ASC")
    fun getAll(): Flow<List<Artist>>

    @Query("SELECT * FROM artist WHERE mbid = :mbid")
    suspend fun getArtistById(mbid: String): Artist?

    @Insert
    suspend fun insert(artist: Artist)

    @Delete
    suspend fun delete(artist: Artist)

    @Query("DELETE FROM artist")
    suspend fun deleteAll()
}