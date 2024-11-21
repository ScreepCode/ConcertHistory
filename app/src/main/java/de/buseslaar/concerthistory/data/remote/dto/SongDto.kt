package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SongDto(
    val name: String,
    val with: ArtistDto? = null,
    val cover: ArtistDto? = null,
    val info: String? = null,
    val tape: Boolean? = null
)
