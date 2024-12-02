package de.buseslaar.concerthistory.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setlist")
data class Setlist(
    @PrimaryKey
    val id: String,

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
