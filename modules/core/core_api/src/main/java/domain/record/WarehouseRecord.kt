package domain.record

import androidx.compose.runtime.Immutable
import domain.department.Department

@Immutable
data class WarehouseRecord(
    val id: String,
    val from: Department,
    val to: Department,
    val targetId: String,
    val count: Int,
    val type: TargetType,
    val timestamp: Long,
)
