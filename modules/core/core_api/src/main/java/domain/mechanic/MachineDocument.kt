package domain.mechanic

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MachineDocument(
    val id: String,
    val name: String,
    val description: String = "",
    @SerialName("machine_id")
    val machineId: String,
)
