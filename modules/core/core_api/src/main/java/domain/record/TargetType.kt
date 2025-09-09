package domain.record

import kotlinx.serialization.Serializable

@Serializable
enum class TargetType {
    PART, OIL
}