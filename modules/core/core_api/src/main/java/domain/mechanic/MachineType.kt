package domain.mechanic

import kotlinx.serialization.Serializable

@Serializable
enum class MachineType {
    TURNING, TURNING_CNC, MILLING_HORIZONTAL, MILLING_UNIVERSAL, MILLING_CNC,
}