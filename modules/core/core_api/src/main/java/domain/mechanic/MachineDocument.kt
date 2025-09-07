package domain.mechanic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineDocument(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("machine_id")
    val machineId: String,
)
