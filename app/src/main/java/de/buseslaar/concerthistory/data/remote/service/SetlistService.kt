package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.SetSearchDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class SetlistService {

    private val apiManger = APIManager()

    /**
     * Search for a setlist in the setlist.fm api
     *
     * @param name the name of the artist, state, venue or tour name
     * @param page the page of the setlist
     * @param city the city of the venue
     * @param venue the name of the venue
     *
     * @return SetSearchDto
     * */
    suspend fun searchSetlist(
        name: String,
        page: Int = 1,
        city: String?,
        venue: String?
    ): SetSearchDto {
        return apiManger.jsonHttpClient.get("search/setlists", {
            url {
                parameters.append("artistName", name)
                parameters.append("p", page.toString())

                if (city != null && city.isNotEmpty()) {
                    parameters.append("city", city)
                }
                if (venue != null && venue.isNotEmpty()) {
                    parameters.append("venueName", venue)
                }
            }
        }).body()
    }

}