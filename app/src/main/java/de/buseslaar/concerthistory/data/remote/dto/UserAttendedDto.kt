package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAttendedDto(
    @SerialName("setlist")
    val setlists: List<SetListDto>
)
