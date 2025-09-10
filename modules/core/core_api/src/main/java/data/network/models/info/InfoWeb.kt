package data.network.models.info

import kotlinx.serialization.Serializable

@Serializable
data class InfoWeb(
    val id: String,
    val name: String,
    val info: String,
)

