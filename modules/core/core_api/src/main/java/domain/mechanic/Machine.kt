package domain.mechanic

import domain.department.MyPoint
import kotlinx.serialization.SerialName

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
    val oils: Map<Oil,Int>,
    val offset: MyPoint,
    @SerialName("rotate_angle")
    val rotateAngle: Float = 0.0f,
    @SerialName("points_for_path")
    val pointsForPath: List<MyPoint>,
)