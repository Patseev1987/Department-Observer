package domain.mechanic

import kotlinx.serialization.Serializable

@Serializable
data class PartOfMachine(
    val id: String,
    val name: String,
    val description: String,
)
