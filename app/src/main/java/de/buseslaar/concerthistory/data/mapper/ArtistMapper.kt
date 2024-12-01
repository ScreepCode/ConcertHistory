package de.buseslaar.concerthistory.data.mapper

import de.buseslaar.concerthistory.data.database.entity.Artist
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto


fun ArtistDto.reduceToEntity(): Artist =
    Artist(
        mbid = mbid,
        name = name,
        sortName = sortName,
        disambiguation = disambiguation,
        url = url
    )
