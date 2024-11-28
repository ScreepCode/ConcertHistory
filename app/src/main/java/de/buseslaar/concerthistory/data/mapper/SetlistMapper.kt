package de.buseslaar.concerthistory.data.mapper

import de.buseslaar.concerthistory.data.database.entity.Setlist
import de.buseslaar.concerthistory.data.remote.dto.SetListDto


fun SetListDto.reduceToEntity(): Setlist =
    Setlist(
        id = id,
        artistName = artist.name,
        versionId = versionId,
        venueName = venue.name,
        venueCity = venue.city.name,
        eventDate = eventDate,
        lastUpdated = lastUpdated
    )
