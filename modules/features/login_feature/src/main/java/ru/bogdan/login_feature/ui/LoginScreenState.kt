package ru.bogdan.login_feature.ui

data class LoginScreenState (
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val login: String = "",
    val password: String = ""
)