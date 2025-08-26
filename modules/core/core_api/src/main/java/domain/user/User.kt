package domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val name: String,
    val surname: String,
    val patronymic: String,
    val role: Role,
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
) {
    companion object {
        val NONE = User(
            name = "Ivan",
            surname = "Ivanov",
            patronymic = "Ivanovch",
            role = Role.WAREHOUSER,
        )
    }
}
