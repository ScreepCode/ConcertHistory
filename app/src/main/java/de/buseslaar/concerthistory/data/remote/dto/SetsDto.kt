package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetsDto(
    val name: String? = null,
    val encore: Int? = 0,
    @SerialName("song")
    val songs: List<SongDto>
)
