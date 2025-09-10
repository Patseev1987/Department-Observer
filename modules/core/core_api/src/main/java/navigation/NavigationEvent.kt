package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationEvent {

    @Serializable
    object LoginScreen : NavigationEvent

    @Serializable
    object Home : NavigationEvent

    @Serializable
    object MachineMain : NavigationEvent

    @Serializable
    object MachinesScreen : NavigationEvent

    @Serializable
    class MachineScreen(val machineId: String = "") : NavigationEvent
}