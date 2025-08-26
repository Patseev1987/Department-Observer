package domain.user

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    WAREHOUSER, WORKER, MANAGER, MECHANIC
}