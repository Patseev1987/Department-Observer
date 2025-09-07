package data.network

import data.dataStore.DataStoreManager
import domain.mechanic.Machine
import domain.user.User
import domain.user.UserRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject

private const val BASE_URL = "http://192.168.0.23:8080"

class NetWorkClientApplication @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private val httpClient = HttpClient(CIO) {
        install(HttpTimeout) {
            socketTimeoutMillis = 10000
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 5000
        }

        install(HttpCodesHandlerPlugin)

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = dataStoreManager.accessToken.first() ?: ""
                    val refreshToken = dataStoreManager.refreshToken.first() ?: ""
                    BearerTokens(
                        accessToken = accessToken,
                        refreshToken = refreshToken,
                    )
                }

            }
        }
    }

    suspend fun getText() = httpClient.get("$BASE_URL/").bodyAsText()

    suspend fun login(login: String, password: String): User {

        val response = httpClient.post("$BASE_URL/login") {
            contentType(ContentType.Application.Json)
            setBody(UserRequest(login, password))
        }
        return response.body()
    }

    suspend fun logout() = httpClient.delete("$BASE_URL/logout")

    suspend fun refreshToken() = httpClient.post("$BASE_URL/token/refresh") {
        //TODO
    }

    suspend fun getUserById(id: String) = httpClient.get("$BASE_URL/users/$id").body<User>()

    suspend fun getMachines() = httpClient.get("$BASE_URL/machines").body<List<Machine>>()

}