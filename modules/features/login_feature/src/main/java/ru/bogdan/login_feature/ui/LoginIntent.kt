package ru.bogdan.login_feature.ui

sealed interface LoginIntent {
    data class ChangeLogin(val login: String) : LoginIntent

    data class ChangePassword(val password: String) : LoginIntent

    data object LogInPressed : LoginIntent
}