package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class MachineDocument(
    val id: String,
    val name: String,
    val description: String,
    val machineModel: MachineModel,
) {
    companion object {
        val NONE = MachineDocument(
            "1",
            "2",
            "3",
            MachineModel.T_16K20,
        )
    }
}
