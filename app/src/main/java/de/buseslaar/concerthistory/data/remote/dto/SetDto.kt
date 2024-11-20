package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SetDto(
    val name : String,
    val encore : Int,
    val song : List<SongDto>
)
