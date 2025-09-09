package data.network.models.mechanic

import kotlinx.serialization.Serializable

@Serializable
data class PartOfMachineWeb(
    val id: String,
    val name: String,
    val description: String,
)
