package domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class User(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    @SerialName("photo_url")
    val photoUrl: String? = null,
    val role: Role,
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("refresh_token")
    val refreshToken: String? = null
) {
    companion object {
        val NONE = User(
            id = "iiiiii",
            name = "Ivan",
            surname = "Ivanov",
            patronymic = "Ivanovch",
            photoUrl = "https://atlas-content-cdn.pixelsquid.com/stock-images/worker-construction-5A5K7r6-600.jpg",
            role = Role.MECHANIC,
        )
    }
}
