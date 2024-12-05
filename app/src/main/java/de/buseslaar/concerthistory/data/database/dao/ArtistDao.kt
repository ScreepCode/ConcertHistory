package de.buseslaar.concerthistory.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.database.entity.ArtistWithSetlists
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artist ORDER BY addedToDatabaseTimestamp ASC")
    fun getAll(): Flow<List<Artist>>

    @Query("SELECT * FROM artist WHERE isFavorite = 1 ORDER BY addedToDatabaseTimestamp ASC")
    fun getAllFavorites(): Flow<List<Artist>>

    @Query("SELECT * FROM artist WHERE mbid = :mbid")
    suspend fun getArtistById(mbid: String): Artist?

    @Insert
    suspend fun insert(artist: Artist)

    @Update
    suspend fun update(artist: Artist)

    @Query("UPDATE artist SET isFavorite = 0")
    suspend fun unfavoriteAll()

    @Delete
    suspend fun delete(artist: Artist)

    @Query("DELETE FROM artist")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM artist WHERE mbid = :artistId")
    suspend fun getArtistByIdWithSetlists(artistId: String): ArtistWithSetlists

    @Query(
        "SELECT COUNT(*) = 0 FROM setlist WHERE artistMbid = :artistId AND isFavorite = 1"
    )
    suspend fun hasNoFavoriteSetlists(artistId: String): Boolean
}