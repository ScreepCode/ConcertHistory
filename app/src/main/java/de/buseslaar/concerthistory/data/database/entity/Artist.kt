package de.buseslaar.concerthistory.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist")
data class Artist(
    @PrimaryKey()
    val mbid: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "sortName")
    val sortName: String,

    @ColumnInfo(name = "disambiguation")
    val disambiguation: String,

    @ColumnInfo(name = "url")
    val url: String
)
