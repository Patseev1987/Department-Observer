package ru.bogdan.main_screen_feature.ui

sealed interface HomeScreenIntent {
    data class NavItemClicked(val observerNavigationItem: ObserverNavigationItem) : HomeScreenIntent
}