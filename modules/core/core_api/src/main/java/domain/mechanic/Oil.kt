package domain.mechanic

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Oil(
    val id: String,
    val name: String,
    val description: String = "",
    @SerialName("image_url")
    val imageUrl: String? = null,
)
