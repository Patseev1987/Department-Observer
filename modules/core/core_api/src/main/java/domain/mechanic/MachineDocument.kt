package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class MachineDocument(
    val id: String,
    val name: String,
    val description: String,
    val machineId: String,
)
