package data.network.models.mechanic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OilWeb(
    val id: String,
    val name: String,
    val description: String = "",
    @SerialName("image_url")
    val imageUrl: String? = null,
)
