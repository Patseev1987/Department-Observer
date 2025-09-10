package data.network.models.mechanic

import domain.mechanic.MachineModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineDocumentWeb(
    val id: String,
    val name: String,
    val description: String,
    @SerialName("machine_model")
    val machineModel: MachineModel,
)
