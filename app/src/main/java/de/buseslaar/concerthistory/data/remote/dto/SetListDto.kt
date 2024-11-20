package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SetListDto(
    val id: String,
    val url : String,
    val versionId: String,
    val eventDate: String,
    val lastUpdated: String,
    val artist: ArtistDto,
    val venue: VenueDto,
    val tour: TourDto? = null,
    val set: List<SetDto>? = null,
    val info : String? = null
)
