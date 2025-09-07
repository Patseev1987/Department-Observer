package domain.mechanic

import kotlinx.serialization.Serializable

@Serializable
enum class MachineState {
    WORKING, STOPPED, REPAIR
}