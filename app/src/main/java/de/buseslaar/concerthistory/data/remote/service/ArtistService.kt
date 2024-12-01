package de.buseslaar.concerthistory.data.remote.service

import de.buseslaar.concerthistory.data.remote.api.APIManager
import de.buseslaar.concerthistory.data.remote.dto.ArtistDto
import de.buseslaar.concerthistory.data.remote.dto.ArtistsDto
import de.buseslaar.concerthistory.data.remote.dto.SetSearchDto
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

    /**
     * Get an artist by its mbid
     * @param mbid the mbid of the author
     */
    suspend fun getArtist(mbid: String): ArtistDto {
        return apiManger.jsonHttpClient.get("artist/${mbid}").body()
    }

    /**
     * Get the last concerts of an artist
     * @param mbid the mbid of the author
     * */
    suspend fun getLastConcerts(mbid: String): SetSearchDto {
        return apiManger.jsonHttpClient.get("artist/${mbid}/setlists").body()
    }
}