package data.network.models.mechanic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineDocumentWeb(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("machine_id")
    val machineId: String,
)
