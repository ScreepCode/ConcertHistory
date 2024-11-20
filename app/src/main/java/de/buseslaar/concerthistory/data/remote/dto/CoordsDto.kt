package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoordsDto(
    val lat: Double,
    val long: Double
)
