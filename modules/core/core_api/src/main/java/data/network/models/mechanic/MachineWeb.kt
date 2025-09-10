package data.network.models.mechanic

import domain.mechanic.MachineState
import domain.mechanic.MachineType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MachineWeb(
    val id: String,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val description: String = "",
    val state: MachineState,
    val type: MachineType,
    @SerialName("year_of_manufactured")
    val yearOfManufacture: Int,
    val parts: Map<PartOfMachineWeb,Int>,
    val docs: List<MachineDocumentWeb>,
    val oils: Map<OilWeb,Int>,
) {
    companion object Companion {
        val NONE =   MachineWeb(
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