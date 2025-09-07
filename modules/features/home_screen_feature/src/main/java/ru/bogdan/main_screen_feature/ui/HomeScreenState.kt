package ru.bogdan.main_screen_feature.ui

import domain.user.Role

data class HomeScreenState(
    val photo: String? = null,
    val name: String = "",
    val surname: String = "",
    val patronymic: String = "",
    val role: Role = Role.MECHANIC,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val navItems: List<ObserverNavigationItem> = emptyList()
)