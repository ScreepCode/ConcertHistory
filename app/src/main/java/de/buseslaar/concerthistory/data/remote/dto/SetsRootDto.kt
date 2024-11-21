package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SetsRootDto(
    @SerialName("set")
    val sets: List<SetsDto>
)
