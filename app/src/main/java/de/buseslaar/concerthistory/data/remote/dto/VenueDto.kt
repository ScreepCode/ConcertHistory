package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VenueDto(
    val id: String,
    val name: String,
    val url: String,
    val city: CityDto,
)
