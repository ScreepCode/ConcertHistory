package de.buseslaar.concerthistory.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAttendedPageDto(
    val itemsPerPage: Int = 0,
    val page: Int = 1,
    val total: Int = 0,
    @SerialName("setlist") val setlists: List<SetListDto>
)