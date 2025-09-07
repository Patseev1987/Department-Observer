package domain.department

import kotlinx.serialization.Serializable

@Serializable
data class MyPoint(
    val x: Float,
    val y: Float,
)
