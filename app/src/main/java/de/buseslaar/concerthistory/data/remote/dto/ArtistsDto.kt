package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistsDto(
    val artist: List<ArtistDto>
)
