package data.network.models.info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResidueWeb(
    @SerialName("residue_id")
    val residueId: String,
    val count: Int,
)
