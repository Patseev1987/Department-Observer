package ru.bogdan.main_screen_feature.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import di.DependenciesProvider
import ru.bogdan.main_screen_feature.di.HomeScreenComponent

@Composable
fun getHomeScreenComponent(): HomeScreenComponent {
    val coreProvider = (LocalContext.current.applicationContext as DependenciesProvider).getCoreProvider()
    return remember {
        HomeScreenComponent.create(coreProvider)
    }
}