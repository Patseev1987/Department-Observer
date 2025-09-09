package data.network.models.record

import domain.department.Department
import domain.record.TargetType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseRecordWeb(
    val id: String,
    val from: Department,
    val to: Department,
    @SerialName("target_id")
    val targetId: String,
    val count: Int,
    val type: TargetType,
    val timestamp: Long,
)
