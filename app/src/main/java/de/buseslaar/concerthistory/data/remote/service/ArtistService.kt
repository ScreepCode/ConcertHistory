package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.ArtistsDto
import io.ktor.client.call.body
import io.ktor.client.request.get

enum class Sort(val sort: String) {
    ARTIST("sortName"),
    RELEVANCE("relevance")
}

class ArtistService {

    private val apiManger = APIManager()

    /**
     * Search for an artist in the setlist.fm api
     *
     * @param name the name of the artist or the group
     * @param sort optional parameter for defining the sorting, defaults to sorting after RELEVANCE
     *
     * @return ArtistsDto
     * */
    suspend fun searchArtist(name: String, sort: Sort = Sort.RELEVANCE): ArtistsDto {
        return apiManger.jsonHttpClient.get("search/artists", {
            url {
                parameters.append("artistName", name)
                parameters.append("sort", sort.sort)
            }
        }).body()
    }

}