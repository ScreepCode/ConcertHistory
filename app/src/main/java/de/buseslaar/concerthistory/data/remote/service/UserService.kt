package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.SetListDto
import de.buseslaar.concerthistory.data.remote.dto.UserAttendedDto
import de.buseslaar.concerthistory.data.remote.dto.UserAttendedPageDto
import de.buseslaar.concerthistory.data.remote.dto.UserDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserService {

    private val apiManager: APIManager = APIManager()

    /**
     * Get a user from the setlist.fm api
     *
     * @param userId the id of the user
     * @return UserDto the user
     */
    suspend fun getUser(userId: String): UserDto {
        return apiManager.jsonHttpClient.get("user/$userId").body()
    }

    /**
     * Fetch a page of attended concerts for a user
     *
     * @param userId the id of the user
     * @param page the page number to fetch
     * @return UserAttendedPageDto containing the attended concerts for the specified page
     */
    private suspend fun fetchUserAttendedPageDto(userId: String, page: Int): UserAttendedPageDto =
        apiManager.jsonHttpClient.get("user/$userId/attended") {
            url {
                parameters.append("p", page.toString())
            }
        }.body()

    /**
     * Get all attended concerts of a user
     *
     * @param userId the id of the user
     * @return UserAttendedDto containing all attended concerts
     */
    suspend fun getAllUserAttended(userId: String): UserAttendedDto {
        val firstPage = fetchUserAttendedPageDto(userId, 1)
        var totalPages = 1
        val allSetlists: MutableList<SetListDto> = mutableListOf()
        with(firstPage) {
            if (setlists.isEmpty()) return UserAttendedDto(emptyList())

            val pageSize = itemsPerPage.takeIf { it > 0 } ?: setlists.size
            totalPages =
                if (total > 0) (total + pageSize - 1) / pageSize else 1

            allSetlists += setlists
        }


        if (totalPages > 1) {
            for (p in 2..totalPages) {
                val pageDto = fetchUserAttendedPageDto(userId, p)
                if (pageDto.setlists.isNotEmpty())
                    allSetlists += pageDto.setlists
                if (pageDto.setlists.size < firstPage.itemsPerPage) break // No more pages to fetch
            }
        }
        return UserAttendedDto(allSetlists)
    }
}