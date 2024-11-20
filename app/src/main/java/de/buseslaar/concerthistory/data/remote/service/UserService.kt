package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.UserAttendedDto
import de.buseslaar.concerthistory.data.remote.dto.UserDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserService {

    private val apiManager: APIManager = APIManager()

    suspend fun getUser(userId: String): UserDto {
        return apiManager.jsonHttpClient.get("user/$userId").body()
    }

    suspend fun getUserAttended(userId: String): UserAttendedDto {
        return apiManager.jsonHttpClient.get("user/$userId/attended").body()
    }

}