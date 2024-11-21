package de.buseslaar.concerthistory.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setlist")
data class Setlist(
    @PrimaryKey()
    val id: String,

    @ColumnInfo(name = "url")
    val url : String,

    @ColumnInfo(name = "versionId")
    val versionId: String,

    @ColumnInfo(name = "eventDate")
    val eventDate: String,

    @ColumnInfo(name = "lastUpdated")
    val lastUpdated: String,
)
