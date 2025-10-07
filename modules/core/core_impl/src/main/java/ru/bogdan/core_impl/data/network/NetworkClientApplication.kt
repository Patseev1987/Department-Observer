package ru.bogdan.core_impl.data.network

import android.util.Log
import data.DataStoreManager
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
import ru.bogdan.core_impl.BuildConfig
import ru.bogdan.core_impl.data.network.models.info.InfoWeb
import ru.bogdan.core_impl.data.network.models.mechanic.MachineWeb
import ru.bogdan.core_impl.data.network.models.user.LoginResponseWeb
import ru.bogdan.core_impl.data.network.models.user.TokenResponseWeb
import ru.bogdan.core_impl.data.network.models.user.UserWeb
import javax.inject.Inject

private const val BASE_URL = BuildConfig.BASE_URL

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
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    allowStructuredMapKeys = true
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

                refreshTokens {
                    oldTokens?.refreshToken?.let {
                        val tokenResponse = refreshToken(it)
                        BearerTokens(
                            accessToken = tokenResponse.accessToken!!,
                            refreshToken = tokenResponse.refreshToken,
                        )
                    }
                }
                sendWithoutRequest { request ->
                    !(request.url.encodedPathSegments.contains("login")
                            || request.url.encodedPath.contains("/auth/"))
                }
            }
        }
    }

    suspend fun login(login: String, password: String): LoginResponseWeb {
        val response = httpClient.post("$BASE_URL/login") {
            Log.d("NavigationEvent", "Detekt test")
            contentType(ContentType.Application.Json)
            setBody(UserRequest(login, password))
        }
        return response.body()
    }

    suspend fun refreshToken(refreshToken: String): TokenResponseWeb {
        val response = httpClient.get("$BASE_URL/auth/refresh-token") {
            takeFrom(this)
            header("Authorization", "Bearer $refreshToken")
        }
        return response.body()
    }


    suspend fun getUser() = httpClient.get("$BASE_URL/users/id").body<UserWeb>()

    suspend fun getMachines() = httpClient.get("$BASE_URL/machines").body<List<MachineWeb>>()

    suspend fun getMachineById(id: String) = httpClient.get("$BASE_URL/machines/$id").body<MachineWeb>()

    suspend fun changeState(newMachine: MachineWeb) = httpClient.post("$BASE_URL/machines/state") {
        contentType(ContentType.Application.Json)
        setBody(newMachine)
    }

    suspend fun getDocById(docId: String) = httpClient.get("$BASE_URL/machines/files/$docId").bodyAsBytes()

    suspend fun getInfo() = httpClient.get("$BASE_URL/info").body<List<InfoWeb>>()
}