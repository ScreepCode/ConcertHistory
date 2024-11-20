package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAttendedDto(
    val setlist: List<SetListDto>
)
