package ru.bogdan.main_screen_feature.ui

import navigation.NavigationEvent

sealed interface HomeScreenUiAction {
    data class ShowToast(val message: String) : HomeScreenUiAction
    data object Logout : HomeScreenUiAction
    data class GoToNextScreen(val navigationEvent: NavigationEvent) : HomeScreenUiAction
}