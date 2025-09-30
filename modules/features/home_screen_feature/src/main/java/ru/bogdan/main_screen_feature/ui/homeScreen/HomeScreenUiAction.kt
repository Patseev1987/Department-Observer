package ru.bogdan.main_screen_feature.ui.homeScreen

sealed interface HomeScreenUiAction {
    data class ShowToast(val message: String) : HomeScreenUiAction
}