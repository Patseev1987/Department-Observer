package domain.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val login: String,
    val password: String,
)
