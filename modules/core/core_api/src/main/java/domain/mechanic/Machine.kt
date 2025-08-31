package domain.mechanic

import androidx.compose.runtime.Immutable
import domain.department.MyPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Machine(
    val id: String,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String? = null,
    val description: String = "",
    val state: MachineState,
    val type: MachineType,
    val parts: List<PartOfMachine>,
    val docs: List<MachineDocument>,
    val oils: List<Oil>,
    val offset: MyPoint,
    val pointsForPath: List<MyPoint>,
)
