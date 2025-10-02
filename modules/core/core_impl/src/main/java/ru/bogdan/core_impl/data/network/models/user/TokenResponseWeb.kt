package ru.bogdan.core_impl.data.network.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseWeb(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("refresh_token")
    val refreshToken: String?
)