package domain.user


data class LoginResponse(
    val username: String,
    val userId: String,
    val token: String? = null,
    val refreshToken: String? = null
) {
    companion object {
        val NONE = LoginResponse(
            username = "Ivan",
            userId = "1337"
        )
    }
}