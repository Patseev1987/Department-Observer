package ru.bogdan.login_feature.ui

sealed interface LoginUiAction {
    data class ShowToast(val message: String) : LoginUiAction
    data object GoToMainScreen : LoginUiAction
}