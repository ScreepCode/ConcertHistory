package de.buseslaar.concerthistory.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ArtistWithSetlists(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "mbid",
        entityColumn = "artistMbid"
    )
    val setlists: List<Setlist>
)