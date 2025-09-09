package domain.record

import domain.department.Department

data class WarehouseRecord(
    val id: String,
    val from: Department,
    val to: Department,
    val targetId: String,
    val count: Int,
    val type: TargetType,
    val timestamp: Long,
)
