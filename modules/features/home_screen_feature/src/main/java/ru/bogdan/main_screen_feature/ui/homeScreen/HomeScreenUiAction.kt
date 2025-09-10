package ru.bogdan.main_screen_feature.ui.homeScreen

import navigation.NavigationEvent

sealed interface HomeScreenUiAction {
    data class ShowToast(val message: String) : HomeScreenUiAction
}