package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    val mbid: String? = null,
    val tmid: Int? = null,
    val name: String,
    val sortName: String,
    val disambiguation: String,
    val url: String
)
