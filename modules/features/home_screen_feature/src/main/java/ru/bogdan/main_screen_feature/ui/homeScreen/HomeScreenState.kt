package ru.bogdan.main_screen_feature.ui.homeScreen

import domain.mechanic.Machine
import domain.user.Role
import ru.bogdan.main_screen_feature.ui.ObserverNavigationItem

data class HomeScreenState(
    val photo: String? = null,
    val name: String = "",
    val surname: String = "",
    val patronymic: String = "",
    val role: Role = Role.MECHANIC,
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val navItems: List<ObserverNavigationItem> = emptyList(),
    val machines: List<Machine> = emptyList()
)