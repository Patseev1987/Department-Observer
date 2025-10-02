package domain.user

import androidx.compose.runtime.Immutable

@Immutable

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val photoUrl: String? = null,
    val role: Role,
    val accessToken: String? = null,
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
