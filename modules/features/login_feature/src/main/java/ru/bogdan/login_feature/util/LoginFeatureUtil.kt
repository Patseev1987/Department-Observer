package ru.bogdan.login_feature.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import di.DependenciesProvider
import ru.bogdan.login_feature.di.LoginComponent

@Composable
fun getLoginComponent(): LoginComponent {
    val coreProvider = (LocalContext.current.applicationContext as DependenciesProvider).getCoreProvider()
    return remember {
        LoginComponent.create(coreProvider)
    }
}