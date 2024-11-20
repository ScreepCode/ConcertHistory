package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SongDto(
    val name : String,
    val with: ArtistDto,
    val cover: ArtistDto,
    val info: String,
    val tape : Boolean
)
