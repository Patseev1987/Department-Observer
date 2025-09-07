package domain.mechanic

import domain.department.MyPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Machine(
    val id: String,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val description: String = "",
    val state: MachineState,
    val type: MachineType,
    @SerialName("year_of_manufactured")
    val yearOfManufacture: Int,
    val parts: List<PartOfMachine>,
    val docs: List<MachineDocument>,
    val oils: Map<String,Int>,
    val offset: MyPoint,
    @SerialName("rotate_angle")
    val rotateAngle: Float = 0.0f,
    @SerialName("points_for_path")
    val pointsForPath: List<MyPoint>,
) {
    companion object {
        val NONE =   Machine(
                id = "111",
        name = "Machine 1",
        imageUrl = "https://5.imimg.com/data5/SELLER/Default/2022/6/JR/EJ/SE/140760167/automatic-turning-lathe-machine-500x500-500x500.jpg",
        description = "Turning machine",
        state = MachineState.WORKING,
        type = MachineType.TURNING,
        docs = emptyList(),
        oils = mapOf(),
        parts = listOf(),
        offset = MyPoint(0.0f, 0.0f),
        pointsForPath = listOf(),
        yearOfManufacture = 1947
        )
    }
}