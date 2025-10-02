package ru.bogdan.core_impl.data.network.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseWeb(
    val username: String,
    val userId: String,
    val token: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
) {
    companion object {
        val NONE = LoginResponseWeb(
            username = "Ivan",
            userId = "1337"
        )
    }
}