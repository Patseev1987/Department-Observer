package domain.user

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    MANAGER, MECHANIC
}