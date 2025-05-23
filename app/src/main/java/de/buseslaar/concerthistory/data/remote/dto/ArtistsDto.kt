package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistsDto(
    @SerialName("artist")
    val artists: List<ArtistDto>
)
