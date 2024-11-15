package de.buseslaar.concerthistory.data.remote.api

import de.buseslaar.concerthistory.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.CancellationException

class APIManager {
    var jsonHttpClient = HttpClient {
        expectSuccess = true

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            url.host = "api.setlist.fm"
            url.protocol = URLProtocol.HTTPS
            url.encodedPath = "/rest/" + url.encodedPath
            contentType(ContentType.Application.Json)
            header("x-api-key", BuildConfig.API_KEY)
        }

        HttpResponseValidator {
            getCustomResponseValidator(this)
        }
    }

    private fun getCustomResponseValidator(responseValidator: HttpCallValidator.Config): HttpCallValidator.Config {
        responseValidator.handleResponseExceptionWithRequest { exception, _ ->
            var exceptionResponseText =
                exception.message ?: "Unknown Error occurred. Please contact your administrator."
            if (exception is ClientRequestException) { //400 errors
                val exceptionResponse = exception.response
                exceptionResponseText = exceptionResponse.bodyAsText()
            } else if (exception is ServerResponseException) { //500 errors
                val exceptionResponse = exception.response
                exceptionResponseText = exceptionResponse.bodyAsText()
            }
            throw CancellationException(exceptionResponseText)
        }
        return responseValidator
    }


}
