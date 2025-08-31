package ru.bogdan.main_screen_feature.ui.homeScreen

import ru.bogdan.main_screen_feature.ui.ObserverNavigationItem

sealed interface HomeScreenIntent {
    data class NavItemClicked(val observerNavigationItem: ObserverNavigationItem) : HomeScreenIntent
}