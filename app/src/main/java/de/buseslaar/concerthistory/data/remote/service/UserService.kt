package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.UserAttendedDto
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
     * Get the setlist of a user from the setlist.fm api
     *
     * @param userId the id of the user
     * @param page the page of the setlist
     * @return UserAttendedDto up to 20 setlists of the user of the page requested via the parameter
     */
    suspend fun getUserAttended(userId: String, page: Int? = 1): UserAttendedDto {
        return apiManager.jsonHttpClient.get("user/$userId/attended", {
            url {
                parameters.append("p", page.toString())
            }
        }).body()
    }

}