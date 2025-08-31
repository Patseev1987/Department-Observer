package navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationEvent {
    @Serializable
    object Back : NavigationEvent
    @Serializable
    class Main(val userId: String) : NavigationEvent
    @Serializable
    object LoginScreen : NavigationEvent
    @Serializable
    class Home(val userId: String = "") : NavigationEvent
    @Serializable
    object WarehousersScreen : NavigationEvent
    @Serializable
    object OilWarehouseScreen : NavigationEvent
    @Serializable
    object PartScreen: NavigationEvent
    @Serializable
    object DepartmentMapScreen: NavigationEvent
    @Serializable
    object MachinesScreen: NavigationEvent
}