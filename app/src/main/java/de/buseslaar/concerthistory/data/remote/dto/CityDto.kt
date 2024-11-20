package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: String,
    val name: String,
    val state : String,
    val stateCode: String,
    val country: CountryDto,
    val coords: CoordsDto,
)
