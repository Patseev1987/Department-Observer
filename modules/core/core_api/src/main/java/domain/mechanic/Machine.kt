package domain.mechanic

import androidx.compose.runtime.Immutable

@Immutable
data class Machine(
    val id: String,
    val name: String,
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
            state = MachineState.WORKING,
            type = MachineType.TURNING,
            docs = emptyList(),
            oils = mapOf(),
            parts = mapOf(),
            yearOfManufacture = 1947
        )
    }
}