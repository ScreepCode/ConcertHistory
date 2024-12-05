package de.buseslaar.concerthistory.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "setlist",
    foreignKeys = [ForeignKey(
        entity = Artist::class,
        parentColumns = ["mbid"],
        childColumns = ["artistMbid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["artistMbid"])]
)
data class Setlist(
    @PrimaryKey
    val setlistId: String,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,

    @ColumnInfo(name = "addedToDatabaseTimestamp")
    val addedToDatabaseTimestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "artistMbid")
    val artistMbid: String,

    @ColumnInfo(name = "artistName")
    val artistName: String,

    @ColumnInfo(name = "versionId")
    val versionId: String,

    @ColumnInfo(name = "venueName")
    val venueName: String,

    @ColumnInfo(name = "venueCity")
    val venueCity: String,

    @ColumnInfo(name = "eventDate")
    val eventDate: String,

    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: String,
)
