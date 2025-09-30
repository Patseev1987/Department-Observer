package domain.mechanic

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
data class Machine(
    val id: String,
    val name: String,
    val model: MachineModel,
    val imageUrl: String? = null,
    val description: String = "",
    val state: MachineState,
    val type: MachineType,
    val yearOfManufacture: Int,
    val parts: Map<PartOfMachine, Int>,
    val docs: List<MachineDocument>,
    val oils: Map<Oil, Int>,
) {
    companion object {
        val NONE = Machine(
            id = "111",
            name = "Machine 1",
            imageUrl = "https://5.imimg.com/data5/SELLER/Default/2022/6/JR/EJ/SE/140760167/automatic-turning-lathe-machine-500x500-500x500.jpg",
            description = "Turning machine",
            model = MachineModel.T_16K20,
            state = MachineState.WORKING,
            type = MachineType.TURNING,
            docs = emptyList(),
            oils = mapOf(
                Oil.NONE to 45
            ),
            parts = mapOf(
                PartOfMachine.NONE to 1
            ),
            yearOfManufacture = 1947
        )
    }
}

@Serializable
enum class MachineModel(val model: String) {
    T_16K20("16K20"),
    T_CTN500_MY("CTN500 MY"),
    X6436("X6436"),
    X5040("X5040"),
    SMU50_30("SMU 50/30"),
}
