package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val code: String, // The countries ISO-Code
    val name: String,
)
